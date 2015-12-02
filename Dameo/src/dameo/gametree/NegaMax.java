package dameo.gametree;

import dameo.GameEngine;
import dameo.evalfunction.CompositeEvaluator;
import dameo.move.Move;
import dameo.strategy.AIStrategy;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    private final CompositeEvaluator evaluator;
    private final long alpha, beta;
    private final int searchDepth;

    public NegaMax(CompositeEvaluator evaluator, int searchDepth, long alpha, long beta) {
        this.evaluator = evaluator;
        this.searchDepth = searchDepth;
        this.alpha = alpha;
        this.beta = beta;
    }

    public NegaMax(int searchDepth) {
        this.evaluator = CompositeEvaluator.createFullEvaluator();
        this.alpha = Long.MIN_VALUE;
        this.beta = Long.MAX_VALUE;
        this.searchDepth = searchDepth;
    }
    
    private Edge alphaBeta(State s, int depth, long alpha, long beta) {
        long score = Long.MIN_VALUE;
        Set<Move> moves = GameEngine.generateLegalMoves(s);
        Edge bestMove = null;
        
        // No more moves, game is lost
        if (moves.isEmpty()) {
            bestMove = new Edge(null, Long.MIN_VALUE);
        }
        if (depth == 0) {
            bestMove = new Edge(null, evaluator.evaluate(s));
        }
        else {
            for (Move m : moves) {
                State copyState = new State(s);
                m.execute(copyState);
                Edge valueNode = alphaBeta(copyState, depth-1, -beta, -alpha);
                if (-valueNode.getValue() > score) {
                    if (depth == 3) {
                        System.out.println("debug");
                    }
                    bestMove = new Edge(m, -valueNode.getValue());
                    score = -valueNode.getValue();
                if (depth == 3) {
                        System.out.println("debug");
                }
                }
                if (score > alpha) alpha = score;
                if (score >= beta) break;
            }
        }
        return bestMove;
    }
    
    @Override
    public Move searchBestMove(State s) {
        Move m = alphaBeta(s, searchDepth, alpha, beta).getMove();
        return m;
    }
    
}
