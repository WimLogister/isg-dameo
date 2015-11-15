package dameo.move;

import dameo.Piece;

/**
 *
 * @author Wim
 */
public class SingleCaptureMove extends Move {
    
    private final int cptOldX;
    private final int cptOldY;
    private final Piece capturedPiece;

    public SingleCaptureMove(int newX, int newY, Piece piece, Piece capturedPiece) {
        super(piece, newX, newY);
        this.capturedPiece = capturedPiece;
        this.cptOldX = capturedPiece.getCol();
        this.cptOldY = capturedPiece.getRow();
    }

    @Override
    public void execute(int[][] board) {
        // Remove capturing piece from previous position on board
        board[piece.getRow()][piece.getCol()] = 0;
        // Put capturing piece on new position on board
        board[newY][newX] = piece.getColor().getValue();
        // Update capturing piece's local information
        piece.setCoords(newY, newX);
        
        // Remove captured piece from board
        board[capturedPiece.getRow()][capturedPiece.getCol()] = 0;
        // Remove captured piece from opponent's piece set
        capturedPiece.removeFromSet();
    }

    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>, Capturing:<%d,%d>", oldX+1,
                oldY+1, newX+1, newY+1, cptOldX+1, cptOldY+1);
    }

}
