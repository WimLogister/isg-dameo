package dameo.evalfunction;

import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class MenCountEvaluationFunction extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s) {
        return 100 * s.getCurrentPlayerPieces().size();
    }
    
}
