package dameo.evalfunction;

import dameo.util.Constants;
import dameo.gameboard.DameoEngine;
import dameo.gametree.State;

/**
 * Evaluation feature that takes into account the mobility of the parameter player,
 * that is, the number of legal moves for this player.
 * @author Wim
 */
public class MobilityEvalFun extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s, Constants.PlayerColors color) {
        if (s.getCurrentPlayerPieces().iterator().next().getColor() == color) {
            return 50 * DameoEngine.generateLegalMoves(s).size();
        }
        else {
            State copyState = new State(s);
            copyState.switchPlayers();
            return 50 * DameoEngine.generateLegalMoves(copyState).size();
        }
    }
    
}
