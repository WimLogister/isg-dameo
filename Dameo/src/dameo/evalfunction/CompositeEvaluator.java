package dameo.evalfunction;

import dameo.util.Constants;
import dameo.gametree.State;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates an evaluation function.
 * Takes a list of evaluation features and executes them on a parameter state.
 * @author Wim
 */
public class CompositeEvaluator {
    
    private final List<EvaluationFunction> evaluators;
    private final Constants.PlayerColors color;

    public CompositeEvaluator(List<EvaluationFunction> evaluators, Constants.PlayerColors color) {
        this.evaluators = evaluators;
        this.color = color;
    }
    
    /**
     * Evaluate parameter state using the list of evaluators/features passed to the
     * constructor of this instance.
     * @param state
     * @return 
     */
    public long evaluate(State state) {
        long sum = 0;
        for (EvaluationFunction f : evaluators) {
            sum += f.evaluatePosition(state, color);
        }
        return sum;
    }
    
    /**
     * Create a composite evaluator that uses all currently available evaluation
     * functions.
     * @param color
     * @return 
     */
    public static CompositeEvaluator createFullEvaluator(Constants.PlayerColors color) {
        List<EvaluationFunction> list = new ArrayList<>();
        list.add(new BasicMaterialDifferenceEvaluator());
        return new CompositeEvaluator(list, color);
    }
}
