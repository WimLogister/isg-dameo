package dameo;

import dameo.gametree.State;
import dameo.move.Move;
import dameo.move.SingleCaptureMove;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class KingPiece extends Piece {
    
    public KingPiece(Piece p) {
        super(p);
    }
    
    @Override
    public int getBoardValue() {
        return super.getBoardValue() * 3;
    }

    @Override
    public Set<SingleCaptureMove> generateCapturingMoves(State s, List<Point> capturedList) {
        return super.generateCapturingMoves(s, capturedList); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Move> generateSingleMoves(State s) {
        Piece[][] board = s.getBoard();
        
        Set<Move> moves = new HashSet<>();
        
        /*
        Variable dir determines the directionality of movement of this piece
        based on its color: for white pieces up and right are positive moves in terms
        in terms of the board's coordinates system, for black left and down are
        positive moves.
        */
        int relativeX = dir*col;
        int relativeY = dir*row;
        
        /*
        Construct non-jumping moves in relative coordinates.
        */
        final int relativeForward = checkY + 1;
        final int relativeBackward = checkY - 1;
        final int relativeLeft = checkX - 1;
        final int relativeRight = checkX + 1;
        
        boolean pieceReached = false;
        
        int x_i = 1;
        int y_i = 1;
        
        /* Check up */
        while(relativeY+x_i <= color.getBoardTopEdge() && !pieceReached) {
            
        }
        
        /* Check down */
        while(relativeY-x_i >= color.getBoardTopEdge() && !pieceReached) {
            
        }
        
        /* Check left */
        while(relativeX-x_i >= color.getBoardTopEdge() && !pieceReached) {
            
        }
        
        /* Check right */
        while(relativeY+x_i <= color.getBoardTopEdge() && !pieceReached) {
            
        }
        
        /* Now check diagonals */
        
        /* Check up and right */
        
        /* Check down and right */
        
        /* Check down and left */
        
        /* Check up and left */
        return super.generateSingleMoves(s); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
