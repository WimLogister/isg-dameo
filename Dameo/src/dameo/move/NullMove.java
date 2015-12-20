package dameo.move;

import dameo.gametree.State;

/**
 * Class used in negamax to store values of nodes deeper than root's direct children.
 * @author Wim
 */
public class NullMove extends Move {

    public NullMove(long value) {
        super(0, 0, 0, 0, value);
    }

    @Override
    public void execute(State state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
