package dameo;

import dameo.gametree.State;
import dameo.move.Move;
import dameo.move.SingleCaptureMove;
import dameo.move.SingleMove;
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
        Piece[][] board = s.getBoard();
        
        Set<SingleCaptureMove> moves = new HashSet<>();
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
        
        boolean pieceReached = false;
        
        /*
        Check up.
        Start by checking we don't move off the board
        */
        while(++relativeY <= color.getBoardTopEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY*dir][col] == null) {
                moves.add(new SingleMove(col, relativeY*dir, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        pieceReached = false;
        relativeY = dir*row;
        
        /* Check down */
        while(--relativeY >= color.getBoardBottomEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY*dir][col] == null) {
                moves.add(new SingleMove(col, relativeY*dir, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        pieceReached = false;
        
        /* Check left */
        while(--relativeX >= color.getBoardLeftEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[row][relativeX] == null) {
                moves.add(new SingleMove(relativeX*dir, row, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        relativeX = dir*col;
        pieceReached = false;
        
        /* Check right */
        while(++relativeX <= color.getBoardRightEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[row][relativeX] == null) {
                moves.add(new SingleMove(relativeX*dir, row, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        
        /* Reset relative coordinates for checking diagonals */
        relativeY = dir*row;
        relativeX = dir*col;
        pieceReached = false;
        
        /* Check up and right */
        while(++relativeX <= color.getBoardRightEdge() &&
                ++relativeY <= color.getBoardTopEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY][relativeX] == null) {
                moves.add(new SingleMove(relativeX*dir, relativeY*dir, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        
        /* Reset relative coordinates for checking next diagonal */
        relativeY = dir*row;
        relativeX = dir*col;
        pieceReached = false;
        
        /* Check down and right */
        while(++relativeX <= color.getBoardRightEdge() &&
                --relativeY >= color.getBoardBottomEdge()&& !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY][relativeX] == null) {
                moves.add(new SingleMove(relativeX*dir, relativeY*dir, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        
        /* Reset relative coordinates for checking next diagonal */
        relativeY = dir*row;
        relativeX = dir*col;
        pieceReached = false;
        
        /* Check down and left */
        while(--relativeX >= color.getBoardLeftEdge() &&
                --relativeY >= color.getBoardBottomEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY][relativeX] == null) {
                moves.add(new SingleMove(relativeX*dir, relativeY*dir, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        
        /* Reset relative coordinates for checking next diagonal */
        relativeY = dir*row;
        relativeX = dir*col;
        pieceReached = false;
        
        /* Check up and left */
        while(--relativeX >= color.getBoardLeftEdge() &&
                ++relativeY <= color.getBoardTopEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY][relativeX] == null) {
                moves.add(new SingleMove(relativeX*dir, relativeY*dir, col, row));
            }
            /* Non-empty square reached, stop iterating in this direction */
            else {
                pieceReached = true;
            }
        }
        return moves;
    }
    
    
    
}
