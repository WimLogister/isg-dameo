package dameo.move;

import dameo.Piece;
import dameo.gametree.State;

/**
 * Single, non-jumping piece move.
 * Now only used for non-jumping king movement.
 * @author Wim
 */
public class SingleMove extends Move {
    
    private int hashCode;

    public SingleMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY, 0);
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
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            result = 31 * result + newX;
            result = 31 * result + newY;
            result = 31 * result + oldX;
            result = 31 * result + oldY;
        }
        return result;
    }
    
    @Override
    public String toString() {
//        boolean kingMove = false;
//        String prefix = ""
//        if (Math.abs(oldX - newX) > 1 || Math.abs(oldY - newY) > 1) {
//            kingMove = true;
//        }
        return String.format("King move <%d,%d>:<%d,%d>", oldX+1,
                oldY+1, newX+1, newY+1);
    }
    
}
