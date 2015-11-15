package dameo.move;

import dameo.Board;
import dameo.DameoUtil;
import dameo.Piece;
import dameo.gametree.State;

/**
 * Dummy class to test move framework.
 * Move is represented as an integer in range [0, 512].
 * @author Wim
 */
public class DummyMove extends Move {
    
    private String move;

    public DummyMove(Piece p) {
        super(p);
        setupMove();
    }
    
    private void setupMove() {
        move = String.format("%d", DameoUtil.getRandomIntFromTo(0, 512));
    }

    @Override
    public void execute(dameo.gametree.State state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return String.format("%s", move);
    }
    
    
    
}
