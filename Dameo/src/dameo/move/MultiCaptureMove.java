package dameo.move;

import dameo.Piece;
import dameo.gametree.State;
import java.awt.Point;
import java.util.List;
import java.util.Stack;

/**
 * Class encapsulting a capturing move, either single or multi-jump.
 * @author Wim
 */
public class MultiCaptureMove extends Move {
    
    private int hashCode;
    private List<Point> capturedPieces;

    public MultiCaptureMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY, 0);
    }

    public MultiCaptureMove(Stack<SingleCaptureMove> moves) {
        super(0, 0, 0, 0, 0);
    }
    
    public MultiCaptureMove(int newX, int newY, int oldX, int oldY, long value,
            List<Point> capturedPieces) {
        super(newX, newY, oldX, oldY, value);
        this.capturedPieces = capturedPieces;
    }

    @Override
    public void execute(State state) {
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

    public List<Point> getCapturedPieces() {
        return capturedPieces;
    }
    
    @Override
    public boolean equals(Object obj) {
        MultiCaptureMove other;
        if (obj instanceof MultiCaptureMove) {
            other = (MultiCaptureMove) obj;
            boolean equals = true;
            /* Disprove that it's the same move */
            if (!(this.newX == other.newX && this.newY == other.newY && this.oldX == other.oldX
                    && this.oldY == other.oldY)) {
                equals = false;
            }
            List<Point> otherCapt = other.getCapturedPieces();
            for (Point p : otherCapt) {
                
            }
            return false;
        }
        else return false;
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
            for (Point p : capturedPieces) {
                result = 31 * result + p.hashCode();
            }
        }
        return result;
    }
    
    
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Point p : capturedPieces) {
            builder.append(String.format("(%d,%d)", (int)p.getX()+1,(int)p.getY()+1));
        }
        return String.format("%d-capture <%d,%d>:<%d,%d>,Capturing:%s",capturedPieces.size(),oldX+1,oldY+1,newX+1,newY+1,builder.toString());
    }

}
