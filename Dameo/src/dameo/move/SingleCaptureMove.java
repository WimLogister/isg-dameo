package dameo.move;

import dameo.Board;
import dameo.Piece;
import dameo.players.Player;

/**
 *
 * @author Wim
 */
public class SingleCaptureMove extends Move {
    
    private final Piece capturedPiece;

    public SingleCaptureMove(int newX, int newY, Piece capturedPiece, Piece piece) {
        super(piece, newX, newY);
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
    }

    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>, Capturing:<%d,%d>", piece.getCol()+1,
                piece.getRow()+1, newX+1, newY+1, capturedPiece.getCol(), capturedPiece.getRow());
    }

    @Override
    public void handleSideEffects(Player opponent) {
        opponent.removePiece(capturedPiece);
    }
    
    
    
}
