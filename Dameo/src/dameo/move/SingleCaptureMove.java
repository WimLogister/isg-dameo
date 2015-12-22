package dameo.move;

import dameo.gameboard.Piece;
import dameo.gametree.State;

/**
 * Instances of this class are put into a list to construct multi-jumps.
 * @author Wim
 */
public class SingleCaptureMove extends Move {
    
    final int captX;
    final int captY;
    private int hashCode;

    public SingleCaptureMove(int newX, int newY, int oldX, int oldY, int captX, int captY) {
        super(newX, newY, oldX, oldY, 0);
        this.captX = captX;
        this.captY = captY;
    }

    @Override
    public void execute(State state) {
        Piece capturingPiece = state.getBoard()[oldY][oldX];        
        // Remove capturing piece from previous position on board
        state.getBoard()[oldY][oldX] = null;
        // Put capturing piece on new position on board
        state.getBoard()[newY][newX] = capturingPiece;
        // Update capturing piece's local information
        capturingPiece.setCoords(newY, newX);
        
        Piece capturedPiece = state.getBoard()[captY][captX];
        // Remove captured piece from board
        state.getBoard()[captY][captX] = null;
        // Remove captured piece from opponent's piece set
        capturedPiece.removeFromSet();
    }
    
    /**
     * Executes the move without removing captured piece from board.
     * Used for finding multi-jumps.
     * @param state 
     */
    public void mockExecute(State state) {
        Piece capturingPiece = state.getBoard()[oldY][oldX];        
        // Remove capturing piece from previous position on board
        state.getBoard()[oldY][oldX] = null;
        // Put capturing piece on new position on board
        state.getBoard()[newY][newX] = capturingPiece;
        // Update capturing piece's local information
        capturingPiece.setCoords(newY, newX);
    }

    @Override
    public String toString() {
        return String.format("Single capture <%d,%d>:<%d,%d>,Capturing:(%d,%d)", oldX+1,
                oldY+1, newX+1, newY+1, captX+1, captY+1);
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
            result = 31 * result + captX;
            result = 31 * result + captY;
        }
        return result;
    }
    
    public int getCaptX() {
        return captX;
    }

    public int getCaptY() {
        return captY;
    }

}
