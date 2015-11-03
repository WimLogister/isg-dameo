package dameo.move;

import dameo.Board;
import dameo.Piece;

/**
 * A move stores information about how the game board changes.
 * So a move stores the piece that is moved, its original position, its new
 * position and any pieces that were jumped over in between.
 * @author Wim
 */
public abstract class Move {
    
    protected Piece piece;
    
    public Move(Piece piece) {
        this.piece = piece;
    }
    
    public abstract void execute(Board board);
    
    public static void generateMoveFromString(String s) {
        
    }
}
