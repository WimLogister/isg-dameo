package dameo.evalfunction;

import dameo.util.Constants;
import dameo.gametree.State;

/**
 * Interface that is implemented classes that model evaluation features.
 * @author Wim
 */
public abstract class EvaluationFunction {
    
    public abstract long evaluatePosition(State s, Constants.PlayerColors color);
}
