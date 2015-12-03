package dameo.evalfunction;

import dameo.Constants;
import dameo.GameEngine;
import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class MobilityEvalFun extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s, Constants.PlayerColors color) {
        if (s.getCurrentPlayerPieces().iterator().next().getColor() == color) {
            return 50 * GameEngine.generateLegalMoves(s).size();
        }
        else {
            State copyState = new State(s);
            copyState.switchPlayers();
            return 50 * GameEngine.generateLegalMoves(copyState).size();
        }
    }
    
}
