package dameo.move;

import dameo.Board;
import dameo.Util;

/**
 * Dummy class to test move framework.
 * Move is represented as an integer in range [0, 512].
 * @author Wim
 */
public class DummyMove extends Move {
    
    private String move;

    public DummyMove() {
        setupMove();
    }
    
    private void setupMove() {
        move = String.format("%d", Util.getRandomIntFromTo(0, 512));
    }

    @Override
    public void execute(Board board) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return String.format("%s", move);
    }
    
    
    
}
