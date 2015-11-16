package dameo.gametree;

import dameo.GameEngine;
import dameo.Piece;
import dameo.evalfunction.EvaluationFunction;
import dameo.evalfunction.MenCountEvaluationFunction;
import dameo.move.Move;
import dameo.strategy.AIStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    private final EvaluationFunction eval;
    private final int alpha, beta;
    private final int searchDepth;

    public NegaMax(EvaluationFunction eval, int searchDepth, int alpha, int beta) {
        this.eval = eval;
        this.searchDepth = searchDepth;
        this.alpha = alpha;
        this.beta = beta;
    }

    public NegaMax(int searchDepth) {
        this.eval = new MenCountEvaluationFunction();
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        this.searchDepth = searchDepth;
    }
    
    private Edge alphaBeta(State s, int depth, int alpha, int beta) {
        int score = Integer.MIN_VALUE;
        Set<Move> moves = GameEngine.generateLegalMoves(s);
        Edge bestMove = null;
        if (moves.isEmpty() || depth == 0) {
            bestMove = new Edge(null, eval.evaluatePosition(s));
        }
        else {
            for (Move m : moves) {
                State copyState = new State(s);
                m.execute(copyState);
                Edge valueNode = alphaBeta(copyState, depth-1, -beta, -alpha);
                if (valueNode.getValue() > score) {
                    bestMove = new Edge(m, valueNode.getValue());
                    score = valueNode.getValue();
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
