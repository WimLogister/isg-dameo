package dameo.move;

import dameo.gametree.State;

/**
 * Move type used to terminate negamax once the iterative deepening time limit runs
 * out.
 * @author Wim
 */
public class TimeOutMove extends Move{

    public TimeOutMove() {
        super(0, 0, 0, 0, 0);
    }


    @Override
    public void execute(State state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
