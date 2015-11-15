package dameo.move;

import dameo.Piece;
import dameo.gametree.State;

/**
 * A move stores information about how the game board changes.
 * So a move stores the piece that is moved, its original position, its new
 * position and any pieces that were jumped over in between.
 * @author Wim
 */
public abstract class Move {
    
    protected final Piece piece;
    final int newX, newY, oldX, oldY;
    
    public Move(Piece piece, int newX, int newY) {
        this.piece = piece;
        this.newX = newX;
        this.newY = newY;
        this.oldX = piece.getCol();
        this.oldY = piece.getRow();
    }
    
    public abstract void execute(State state);
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
