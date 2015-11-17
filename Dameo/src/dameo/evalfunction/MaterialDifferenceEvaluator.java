package dameo.evalfunction;

import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class MaterialDifferenceEvaluator extends EvaluationFunction {

    @Override
    public long evaluatePosition(State s) {
        return 100 * (s.getCurrentPlayerPieces().size() - s.getOpponentPieces().size());
    }
    
}
