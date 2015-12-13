package dameo.evalfunction;

import dameo.Constants;
import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class AdvancedMaterialDifferenceEvaluator extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s, Constants.PlayerColors color) {
        State checkState = s;
        if (s.getCurrentPlayerPieces().iterator().next().getColor() != color) {
            checkState = new State(s);
            checkState.switchPlayers();
        }
        int sum = 0;
        for (Piece p : checkState.getCurrentPlayerPieces()) {
            if (p instanceof KingPiece) {
                sum += 9;
            }
            else {
                sum += 1;
            }
        }
        for (Piece p : checkState.getOpponentPieces()) {
            if (p instanceof KingPiece) {
                sum -= 9;
            }
            else {
                sum -= 1;
            }
        }
        return 500 * sum;
    }
    
}
