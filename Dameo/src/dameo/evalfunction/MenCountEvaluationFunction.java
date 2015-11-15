package dameo.evalfunction;

import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class MenCountEvaluationFunction extends EvaluationFunction {

    @Override
    public int evaluatePosition(State s) {
        return s.getCurrentPlayer().getPieces().size();
    }
    
}
