package dameo.move;

import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class UndoMove extends Move {

    public UndoMove(int newX, int newY, int oldX, int oldY, long value) {
        super(newX, newY, oldX, oldY, value);
    }

    public UndoMove() {
        super(0, 0, 0, 0, 0);
    }
    
    @Override
    public void execute(State state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "UndoMove";
    }
    
    
}
