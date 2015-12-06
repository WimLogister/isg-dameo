package dameo.evalfunction;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class MaterialDifferenceEvaluator extends EvaluationFunction {

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
