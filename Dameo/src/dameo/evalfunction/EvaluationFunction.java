package dameo.evalfunction;

import dameo.Constants;
import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public abstract class EvaluationFunction {
    
    public abstract long evaluatePosition(State s, Constants.PlayerColors color);
}
