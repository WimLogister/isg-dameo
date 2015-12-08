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
        if (s.getCurrentPlayerPieces().iterator().next().getColor() == color) {
            int sum = 0;
            for (Piece p : s.getCurrentPlayerPieces()) {
                if (p instanceof KingPiece) {
                    sum += 9;
                }
            }
        }
        else {
            State copyState = new State(s);
            copyState.switchPlayers();
        }
        return 0;
    }
    
}
