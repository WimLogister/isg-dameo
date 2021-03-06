package dameo.evalfunction;

import dameo.util.Constants;
import dameo.gametree.State;

/**
 * Evaluation feature that counts the material difference between the two players.
 * The player who is doing the search (MAX player) is passed as parameter color.
 * @author Wim
 */
public class BasicMaterialDifferenceEvaluator extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s, Constants.PlayerColors color) {
        if (s.getCurrentPlayerPieces().iterator().next().getColor() == color) {
            return 500 * (s.getCurrentPlayerPieces().size() - s.getOpponentPieces().size());
        }
        else {
            State copyState = new State(s);
            copyState.switchPlayers();
            long score = 500 * (copyState.getCurrentPlayerPieces().size() - copyState.getOpponentPieces().size());
            return score;
        }
    }
    
}
