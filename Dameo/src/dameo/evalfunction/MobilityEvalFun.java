package dameo.evalfunction;

import dameo.GameEngine;
import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class MobilityEvalFun extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s) {
        return 100 * GameEngine.generateLegalMoves(s).size();
    }
    
}
