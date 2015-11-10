package dameo.move;

import dameo.Board;
import dameo.Piece;

/**
 *
 * @author Wim
 */
public class SingleCaptureMove extends Move {
    
    private final int newX, newY;
    private final Piece capturedPiece;

    public SingleCaptureMove(int newX, int newY, Piece capturedPiece, Piece piece) {
        super(piece);
        this.newX = newX;
        this.newY = newY;
        this.capturedPiece = capturedPiece;
    }

    @Override
    public void execute(int[][] board) {
        // Remove piece from previous position on board
        board[piece.getRow()][piece.getCol()] = 0;
        // Put piece on new position on board
        board[newY][newX] = piece.getColor().getValue();
        
        // Update capturing piece's information
        piece.setCoords(newY, newX);
        
        // Update captured piece's information
        capturedPiece.getPlayer().removePiece(piece);
    }
    
}
