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
    
    List<Point> capturedPieces;

    public MultiCaptureMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY);
    }

    public MultiCaptureMove(Stack<SingleCaptureMove> moves) {
        super(0, 0, 0, 0);
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
        
        super.promotePiece(state);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Point p : capturedPieces) {
            builder.append(String.format("(%d,%d)", (int)p.getX()+1,(int)p.getY()+1));
        }
        return String.format("Multi-capture <%d,%d>:<%d,%d>,Capturing:%s",oldX+1,oldY+1,newX+1,newY+1,builder.toString());
    }

    @Override
    public int compareTo(Move o) {
        if (this.oldX < o.oldX) {
            return -1;
        }
        if (this.oldX == o.oldX) {
            if (this.oldY < o.oldY) {
                return -1;
            }
            if (this.oldY == o.oldY) {
                return 0;
            }
            return 1;
        }
        else {
            return 1;
        }
    }
    
    

}
