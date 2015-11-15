package dameo.evalfunction;

import dameo.players.Player;

/**
 *
 * @author Wim
 */
public abstract class EvaluationFunction {
    
    abstract double evaluatePosition(int[][] board, Player currentPlayer);
}
