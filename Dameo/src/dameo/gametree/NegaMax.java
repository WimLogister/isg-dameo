package dameo.gametree;

import dameo.GameEngine;
import dameo.evalfunction.EvaluationFunction;
import dameo.move.Move;
import dameo.strategy.AIStrategy;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    private final EvaluationFunction eval;

    public NegaMax(EvaluationFunction eval) {
        this.eval = eval;
    }
    
    private int alphaBeta(State s, int depth, int alpha, int beta) {
        int score = Integer.MIN_VALUE;
        Set<Move> moves = GameEngine.generateLegalMoves(s);
        if (moves.isEmpty() || depth == 0) {
            return eval.evaluatePosition(s);
        }
        else {
        }
        return score;
    }
    
    private boolean terminalNodeCheck(State s) {
        return GameEngine.generateLegalMoves(s).isEmpty();
    }

    @Override
    public Move searchBestMove(State s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
