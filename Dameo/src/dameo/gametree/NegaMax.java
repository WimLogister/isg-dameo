package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.evalfunction.CompositeEvaluator;
import dameo.move.Move;
import dameo.move.NullMove;
import dameo.move.TimeOutMove;
import dameo.strategy.AIStrategy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Class implementing negamax with alpha-beta pruning.
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    protected static int nodesExpanded;
    private final CompositeEvaluator evaluator;
    protected final long alpha, beta;
    protected int iterationSearchDepth;
    public final int[] nodeLog = new int[100];
    protected int logCounter = 0;
    private final TranspositionTable tt = new TranspositionTable(20, 44);
    protected int highestForcedSearchDepth = 0;
    protected final long timeLimit;

    public NegaMax(int iterationSearchDepth, Constants.PlayerColors color, long timeLimit) {
        this.evaluator = CompositeEvaluator.createFullEvaluator(color);
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        this.iterationSearchDepth = iterationSearchDepth;
        this.timeLimit = timeLimit;
    }

    /**
     * Negamax algorithm with alpha-beta pruning.
     * @param s the root of this subtree
     * @param depth the depth to which the subtree below this node will be searched
     * @param alpha alpha (lower) bound
     * @param beta beta (upper) bound
     * @param color the negamax "color", signifying which player's turn it is
     * @param startTime system time at which the iterative deepening cycle started
     * @return 
     */
    protected Move alphaBeta(State s, int depth, long alpha, long beta, int color, long startTime) {
        /*
        Iterative deepening time limit has run out
        */
        if (System.currentTimeMillis() - startTime >= timeLimit*1000) {
            return new TimeOutMove();
        }
        boolean insufficientDepth = false;
        highestForcedSearchDepth = Math.max(depth, highestForcedSearchDepth);
        long origAlpha = alpha;
        Move ttMove = null;
        TranspositionTable.TableCheckResultTypes ttentryflag = tt.checkTable(s);
        
        /* storedEntry is the entry returned by checking the transposition table.
        If the primary hash key was not present in the table, storedEntry is a new
        table entry with default values except for its hash code. */
        TableEntry storedEntry = tt.getCachedEntry();
        if (ttentryflag == TranspositionTable.TableCheckResultTypes.VALID) {
            /* This value had already been stored in TT. Check searched depth */
            if (storedEntry.getSearchDepth() >= this.iterationSearchDepth-depth) {
                /*
                Exact value for this state was stored in TT at an appropriate
                search depth. Adapt current search bounds according to stored values.
                */
                if (storedEntry.getValueFlag()==TranspositionTable.TableValueFlagTypes.EXACT) {
                    /* No need to adapt bounds: already have exact value for this state */
                    return new NullMove(storedEntry.getStateValue());
                }
                else if (storedEntry.getValueFlag()==TranspositionTable.TableValueFlagTypes.LOWER) {
                    /*
                    Stored value is a lower bound and exceeds lower bound in this
                    search line: adapt current alpha accordingly.
                    */
                    alpha = Math.max(alpha,storedEntry.getStateValue());
                }
                else if (storedEntry.getValueFlag()==TranspositionTable.TableValueFlagTypes.UPPER) {
                    /*
                    Stored value is an upper bound and is lower than upper bound
                    in this search line: adapt current beta accordingly.
                    */
                    beta = Math.min(beta,storedEntry.getStateValue());
                }
                if (alpha >= beta) {
                    /*
                    By looking at the stored bounds for this nodes, we have
                    determined an exact value for this node. Return this value.
                    */
                    return new NullMove(storedEntry.getStateValue());
                }
            }
            /*
            Can't use retrieved value directly: node in table has not been 
            investigated as deeply as current search line. Need to search using current
            parameters. However, can use stored best move as first try.
            */
            insufficientDepth = true;
            ttMove = storedEntry.getBestMove();
        }
        long score = Integer.MIN_VALUE;
        List<Move> moves = this.negamaxMoveGeneration(s, depth);
        nodesExpanded++;
        Move bestMove = null;
        if (insufficientDepth) {
            int removeIndex = 0;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).hashCode() == ttMove.hashCode()) {
                    removeIndex = i;
                }
            }
            moves.remove(removeIndex);
            moves.add(0, ttMove);
        }
        
        // Don't perform search if there is only one legal move
        if (depth == 0 && moves.size() == 1) {
            iterationSearchDepth = IDNegamax.maxSearchDepth;
            Move m = moves.iterator().next();
            return m;
        }
        
        // No legal moves in this state, current player loses game
        if (moves.isEmpty()) {
            return new NullMove(color*Integer.MIN_VALUE);
        }
        
        // Opponent has no more moves, win for current player.
        if (s.getOpponentPieces().isEmpty()) {
            return new NullMove(color*Integer.MAX_VALUE);
        }
        
        // Leaf node reached, return evaluation of current state
        if (depth >= iterationSearchDepth) {
            return new NullMove(color*evaluator.evaluate(s));
        }
        else {
            for (Move m : moves) {
                /*
                Move to next layer of tree by executing this move on copy of
                current state and performing alpha-beta search on new state.
                */
                State copyState = new State(s);
                m.execute(copyState);
                copyState.switchPlayers();
                
                Move valueMove = alphaBeta(copyState, depth+1, -beta, -alpha, -color, startTime);
                if (valueMove instanceof TimeOutMove) {
                    return valueMove;
                }
                m.setValue(-valueMove.getValue());
                /*
                For iterative deepening, save root moves for ordering in next
                iteration.
                */
                if (depth == 0)
                    this.disposeOfRootMove(m);
                
                /*
                Update bounds and perform cutoff.
                */
                if (-valueMove.getValue() > score) {
                    bestMove = m;
                    score = -valueMove.getValue();
                }
                if (score > alpha) alpha = score;
                if (score >= beta) break;
            }
        }
        /* TODO: need to check when this situation occurs */
        if (bestMove instanceof NullMove || bestMove == null) {
            bestMove = moves.iterator().next();
            bestMove.setValue(color*Integer.MIN_VALUE);
        }
        
        /* Precompute some values to be stored in transposition table entry */
        TranspositionTable.TableValueFlagTypes entryFlag = null;
        if (bestMove.getValue() <= origAlpha) {
            entryFlag = TranspositionTable.TableValueFlagTypes.UPPER;
        }
        else if (bestMove.getValue() >= beta) {
            entryFlag = TranspositionTable.TableValueFlagTypes.LOWER;
        }
        else {
            entryFlag = TranspositionTable.TableValueFlagTypes.EXACT;
        }
        
        final int entryDepth = iterationSearchDepth - depth;
        
        storedEntry.setStateValue(bestMove.getValue());
        storedEntry.setValueType(entryFlag);
        storedEntry.setSearchDepth(entryDepth);
        storedEntry.setBestMove(bestMove);
        
        return bestMove;
    }
    
    /**
     * Abstract method used to process moves at the root node.
     * Is meant primarily for move ordering at the root node for iterative
     * deepening.
     * @param move
     * @param depth 
     */
    protected void disposeOfRootMove(Move move) {}
    
    /**
     * Encapsulates node expansion and move ordering.
     * The returned collection of nodes should be traversed in the indicated
     * order.
     * @param s
     * @param depth
     * @return 
     */
    protected List<Move> negamaxMoveGeneration(State s, double depth) {
        return DameoEngine.generateLegalMoves(s);
    }

    public int getHighestForcedSearchDepth() {
        return highestForcedSearchDepth;
    }
    
    @Override
    public Move searchBestMove(State s) {
        nodesExpanded = 0;
        Move m = alphaBeta(s, 0, alpha, beta, 1, 0);
        nodeLog[logCounter++] = nodesExpanded;
        if (logCounter > 25) {
            BufferedWriter writer = null;
            try {
                File logFile = new File("IDnegamax-nodes");
                writer = new BufferedWriter(new FileWriter(logFile));
                for (int i : nodeLog) {
                    writer.write(String.format("%d\n", i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    writer.close();
                } catch (Exception e) {}
            }
        }
        return m;
    }
    
}
