package dameo.evalfunction;

import dameo.gametree.State;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wim
 */
public class CompositeEvaluator {
    
    private final List<EvaluationFunction> evaluators;

    public CompositeEvaluator(List<EvaluationFunction> evaluators) {
        this.evaluators = evaluators;
    }
    
    public long evaluate(State state) {
        long sum = 0;
        for (EvaluationFunction f : evaluators) {
            sum += f.evaluatePosition(state);
        }
        return sum;
    }
    
    /**
     * Create a composite evaluator that uses all currently available evaluation
     * functions.
     * @return 
     */
    public static CompositeEvaluator createFullEvaluator() {
        List<EvaluationFunction> list = new ArrayList<>();
        list.add(new MaterialDifferenceEvaluator());
        list.add(new MobilityEvalFun());
        return new CompositeEvaluator(list);
    }
}
