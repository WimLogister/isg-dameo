package dameo.move;

import dameo.Piece;
import dameo.gametree.State;
import java.awt.Point;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class MultiCaptureMove extends Move {
    
    Stack<SingleCaptureMove> moves;
    List<Point> capturedPieces;

    public MultiCaptureMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY);
    }

    public MultiCaptureMove(Stack<SingleCaptureMove> moves) {
        super(0, 0, 0, 0);
        this.moves = moves;
    }
    
    public MultiCaptureMove(int newX, int newY, int oldX, int oldY,
            List<Point> capturedPieces) {
        super(newX, newY, oldX, oldY);
        this.capturedPieces = capturedPieces;
    }
    
    
    
    

    @Override
    public void execute(State state) {
//        throw new UnsupportedOperationException("Not supported yet.");
        Piece [][] board = state.getBoard();
        Piece capturingPiece = board[oldY][oldX];
        // Remove capturing piece from previous position on board
        board[oldY][oldX] = null;
        // Put capturing piece on new position on board
        board[newY][newX] = capturingPiece;
        // Update capturing piece's local information
        capturingPiece.setCoords(newY, newX);
        
        // Remove each captured piece from the board
        for (Point p : capturedPieces) {
            Piece capturedPiece = board[p.y][p.x];
            board[p.y][p.x] = null;
            capturedPiece.removeFromSet();
        }
                
    }
    
}
