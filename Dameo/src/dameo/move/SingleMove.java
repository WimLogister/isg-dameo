package dameo.move;

import dameo.Piece;
import dameo.gametree.State;

/**
 * Single, non-jumping piece move
 * @author Wim
 */
public class SingleMove extends Move {

    public SingleMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY);
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    @Override
    public void execute(State state) {
        Piece p = state.getBoard()[oldY][oldX];
        if (p == null) {
            System.out.println("Check for empty piece");
        }
        // Remove piece from previous position on board
        state.getBoard()[oldY][oldX] = null;
        // Put piece on new position on board
        state.getBoard()[newY][newX] = p;
        
        // Update piece information
        p.setCoords(newY, newX);
        
        super.promotePiece(state);
    }


    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>", oldX+1,
                oldY+1, newX+1, newY+1);
    }
    
}
