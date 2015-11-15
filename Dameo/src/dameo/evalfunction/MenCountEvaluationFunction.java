package dameo.evalfunction;

import dameo.players.Player;

/**
 *
 * @author Wim
 */
public class MenCountEvaluationFunction extends EvaluationFunction {

    @Override
    double evaluatePosition(int[][] board, Player currentPlayer) {
        return currentPlayer.getPieces().size();
    }
    
}
