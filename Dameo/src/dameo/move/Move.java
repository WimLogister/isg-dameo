package dameo.move;

import dameo.Constants;
import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;

/**
 * A move stores information about how the game board changes.
 * So a move stores the piece that is moved, its original position, its new
 * position and any pieces that were jumped over in between.
 * @author Wim
 */
public abstract class Move {
    
    final int newX, newY, oldX, oldY;
    
    public Move(int newX, int newY, int oldX, int oldY) {
        this.newX = newX;
        this.newY = newY;
        this.oldX = oldX;
        this.oldY = oldY;
    }
    
    public abstract void execute(State state);
    
    void promotePiece(State s) {
        Piece[][] board = s.getBoard();
        Piece p = board[newY][newX];
        // Check for white player
        if (p.getColor() == Constants.PlayerColors.WHITE) {
            // Check if last row reached
            if (newY == 7) {
                p.removeFromSet();
                board[newY][newX] = new KingPiece(p);
                s.getCurrentPlayerPieces().add(p);
            }
        }
        else {
            // Check if last row reached
            if (newY == 0) {
                p.removeFromSet();
                board[newY][newX] = new KingPiece(p);
                s.getCurrentPlayerPieces().add(p);
            }
        }
    }
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
