package dameo.move;

import dameo.Piece;
import dameo.gametree.State;

/**
 *
 * @author Wim
 */
public class SingleCaptureMove extends Move {
    
    final int captX;
    final int captY;

    public SingleCaptureMove(int newX, int newY, int oldX, int oldY, int captX, int captY) {
        super(newX, newY, oldX, oldY);
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

    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>, Capturing:<%d,%d>", oldX+1,
                oldY+1, newX+1, newY+1, captX+1, captY+1);
    }

}
