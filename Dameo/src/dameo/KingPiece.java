package dameo;

import dameo.gametree.State;
import dameo.move.Move;
import dameo.move.SingleCaptureMove;
import dameo.move.SingleMove;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import dameo.Piece;

/**
 *
 * @author Wim
 */
public class KingPiece extends Piece {
    
    public KingPiece(Piece p) {
        super(p);
    }
    
    public KingPiece(int row, int col, Constants.PlayerColors color, Set<Piece> pieceSet) {
        super(row, col, color, pieceSet);
    }
    
    @Override
    public int getBoardValue() {
        return super.getBoardValue() * 3;
    }

    @Override
    public Set<SingleCaptureMove> generateCapturingMoves(State s, List<Point> capturedList) {
        Piece[][] board = s.getBoard();
        
        Set<SingleCaptureMove> moves = new HashSet<>();
        
        /*
        Variable dir determines the directionality of movement of this piece
        based on its color: for white pieces up and right are positive moves in terms
        in terms of the board's coordinates system, for black left and down are
        positive moves.
        */
        int relativeX = dir*col;
        int relativeY = dir*row;
        
        boolean pieceReached = false;
        
        /* Check up */
        while(++relativeY <= color.getBoardTopEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY*dir][col] != null) {
                pieceReached = true;
            }
        }
        relativeY--;
        /*
        If we've reached an enemy piece, start iterating and adding
        SingleCaptureMoves for each empty square.
        */
        if (pieceReached && board[relativeY*dir][col].getColor().getValue() == color.getOpponent() &&
                !Piece.listContainsPoint(capturedList, new Point(col, relativeY*dir))){
            int captY = relativeY*dir;
            while (++relativeY <= color.getBoardTopEdge() &&
                    board[relativeY*dir][col] == null) {
                moves.add(new SingleCaptureMove(col, relativeY*dir, col, row, col, captY));
            }
        }
        pieceReached = false;
        relativeX = dir*col;
        relativeY = dir*row;
        
        
        /* Check down */
        while(--relativeY >= color.getBoardBottomEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[relativeY*dir][col] != null) {
                pieceReached = true;
            }
        }
        relativeY++;
        /*
        If we've reached an enemy piece, start iterating and adding
        SingleCaptureMoves for each empty square, unless the Piece had already
        been captured before.
        */
        if (pieceReached && board[relativeY*dir][col].getColor().getValue() == color.getOpponent() &&
                !Piece.listContainsPoint(capturedList, new Point(col, relativeY*dir))) {
            int captY = relativeY*dir;
            while (--relativeY >= color.getBoardBottomEdge() &&
                    board[relativeY*dir][col] == null) {
                moves.add(new SingleCaptureMove(col, relativeY*dir, col, row, col, captY));
            }
        }
        pieceReached = false;
        relativeX = dir*col;
        relativeY = dir*row;
        
        /* Check left */
        while(--relativeX >= color.getBoardLeftEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[row][relativeX*dir] != null) {
                pieceReached = true;
            }
        }
        relativeX++;
        /*
        If we've reached an enemy piece, start iterating and adding
        SingleCaptureMoves for each empty square.
        */
        if (pieceReached && board[row][relativeX*dir].getColor().getValue() == color.getOpponent() &&
                !Piece.listContainsPoint(capturedList, new Point(relativeX*dir, row))) {
            int captX = relativeX*dir;
            while (--relativeX >= color.getBoardLeftEdge() &&
                    board[row][relativeX*dir] == null) {
                moves.add(new SingleCaptureMove(relativeX*dir, row, col, row, captX, row));
            }
        }
        pieceReached = false;
        relativeX = dir*col;
        relativeY = dir*row;
        
        /* Check right */
        while(++relativeX <= color.getBoardRightEdge() && !pieceReached) {
            /* Check that square is empty */
            if (board[row][relativeX*dir] != null) {
                pieceReached = true;
            }
        }
        relativeX--;
        /*
        If we've reached an enemy piece, start iterating and adding
        SingleCaptureMoves for each empty square.
        */
        if (pieceReached && board[row][relativeX*dir].getColor().getValue() == color.getOpponent() &&
                !Piece.listContainsPoint(capturedList, new Point(relativeX*dir, row))) {
            int captX = relativeX*dir;
            while (++relativeX <= color.getBoardRightEdge() &&
                    board[row][relativeX*dir] == null) {
                moves.add(new SingleCaptureMove(relativeX*dir, row, col, row, captX, row));
            }
        }
        return moves;
    }
    
    public static void copyIntoSet(Piece origPiece, Set<Piece> newSet) {
        newSet.add(new KingPiece(origPiece.getRow(), origPiece.getCol(), origPiece.getColor(), newSet));
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
        
        /* Check up */
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
            if (board[row][relativeX*dir] == null) {
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
            if (board[row][relativeX*dir] == null) {
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
            if (board[relativeY*dir][relativeX*dir] == null) {
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
            if (board[relativeY*dir][relativeX*dir] == null) {
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
            if (board[relativeY*dir][relativeX*dir] == null) {
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
            if (board[relativeY*dir][relativeX*dir] == null) {
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
