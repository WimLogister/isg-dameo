package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.evalfunction.CompositeEvaluator;
import dameo.move.Move;
import dameo.move.NullMove;
import dameo.strategy.AIStrategy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    protected static int nodesExpanded;
    private final CompositeEvaluator evaluator;
    protected final long alpha, beta;
    protected int searchDepth;
    private final int negamaxColor;
    public final int[] nodeLog = new int[100];
    protected int logCounter = 0;

    public NegaMax(int searchDepth, Constants.PlayerColors color) {
        this.evaluator = CompositeEvaluator.createFullEvaluator(color);
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        this.searchDepth = searchDepth;
        this.negamaxColor = color.getNegamaxColor();
    }
    
    protected Move alphaBeta(State s, int depth, long alpha, long beta, int color) {
        long score = Integer.MIN_VALUE;
        Collection<Move> moves = this.negamaxMoveGeneration(s, depth);
        nodesExpanded++;
        Move bestMove = null;
        
        // Don't perform search if there is only one legal move
        if (depth == 0 && moves.size() == 1) {
            searchDepth = IDNegamax.maxSearchDepth;
            Move m = moves.iterator().next();
            return m;
        }
        
        // No legal moves in this state, current player loses game
        if (moves.isEmpty()) {
            return new NullMove(color*Integer.MIN_VALUE);
//            bestMove = new Edge(null, color*Integer.MIN_VALUE);
        }
        
        // Opponent has no more moves, win for current player.
        if (s.getOpponentPieces().isEmpty()) {
            return new NullMove(color*Integer.MAX_VALUE);
        }
        
        // Leaf node reached, return evaluation of current state
        if (depth == searchDepth) {
            // De fout moet bijna zeker hier zitten
            return new NullMove(color*evaluator.evaluate(s));
//            bestMove = new Edge(null, color*evaluator.evaluate(s));
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
                Move valueMove = alphaBeta(copyState, depth+1, -beta, -alpha, -color);
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
//                    bestMove = new Edge(m, -valueNode.getValue());
                    bestMove = m;
                    score = -valueMove.getValue();
                }
                if (score > alpha) alpha = score;
                if (score >= beta) break;
            }
        }
        if (bestMove instanceof NullMove || bestMove == null) {
            bestMove = moves.iterator().next();
            bestMove.setValue(color*Integer.MIN_VALUE);
//            bestMove = new Edge(moves.iterator().next(), color*Integer.MIN_VALUE);
        }
        if (bestMove == null) {
            System.out.println("debug");
        }
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
    protected Collection<Move> negamaxMoveGeneration(State s, int depth) {
        return DameoEngine.generateLegalMoves(s);
    }
    
    @Override
    public Move searchBestMove(State s) {
        nodesExpanded = 0;
        Move m = alphaBeta(s, 0, alpha, beta, 1);
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
