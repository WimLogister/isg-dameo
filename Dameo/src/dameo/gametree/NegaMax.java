package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.evalfunction.CompositeEvaluator;
import dameo.move.Move;
import dameo.strategy.AIStrategy;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    private final CompositeEvaluator evaluator;
    protected final long alpha, beta;
    protected int searchDepth;
    private final int negamaxColor;

    public NegaMax(int searchDepth, Constants.PlayerColors color) {
        this.evaluator = CompositeEvaluator.createFullEvaluator(color);
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        this.searchDepth = searchDepth;
        this.negamaxColor = color.getNegamaxColor();
    }
    
    protected Edge alphaBeta(State s, int depth, long alpha, long beta, int color) {
        long score = Integer.MIN_VALUE;
        Collection<Move> moves = this.negamaxMoveGeneration(s, depth);
        Edge bestMove = null;
        
        // Don't perform search if there is only one legal move
        if (depth == 0 && moves.size() == 1) {
            return new Edge(moves.iterator().next(),0);
        }
        
        // No legal moves in this state, current player loses game
        if (moves.isEmpty()) {
            bestMove = new Edge(null, color*Integer.MIN_VALUE);
        }
        
        // Leaf node reached, return evaluation of current state
        else if (depth == searchDepth) {
            bestMove = new Edge(null, color*evaluator.evaluate(s));
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
                Edge valueNode = alphaBeta(copyState, depth+1, -beta, -alpha, -color);
                
                /*
                For iterative deepening, save root moves for ordering in next
                iteration.
                */
                if (depth == 0)
                    this.disposeOfRootMove(valueNode);
                
                /*
                Update bounds and perform cutoff.
                */
                if (-valueNode.getValue() > score) {
                    bestMove = new Edge(m, -valueNode.getValue());
                    score = -valueNode.getValue();
                }
                if (score > alpha) alpha = score;
                if (score >= beta) break;
            }
        }
        if (bestMove == null) {
            bestMove = new Edge(moves.iterator().next(), color*Integer.MIN_VALUE);
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
    protected void disposeOfRootMove(Edge move) {}
    
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
        Move m = alphaBeta(s, 0, alpha, beta, 1).getMove();
        return m;
    }
    
}
