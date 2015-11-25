package dameo;

import dameo.gametree.State;
import dameo.move.Move;
import dameo.move.SingleMove;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Models a regular (non-king) Dameo piece.
 * @author Wim
 */
public class Piece {
    
    private int row, col;
    private final Constants.PlayerColors color;
    private Set<Piece> pieceSet;
    private int hashCode;

    private Piece(int row, int col, Constants.PlayerColors color, Set<Piece> pieceSet) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.pieceSet = pieceSet;
    }

    public static Piece findPiece(Set<Piece> pieceSet, int x, int y) {
        Iterator<Piece> it = pieceSet.iterator();
        Piece p = null;
        while (it.hasNext()) {
            Piece match = it.next();
            if (match.getCol() == x && match.getRow() == y) {
                p = match;
            }
        }
        return p;
    }
    
    public static void copyIntoSet(Piece origPiece, Set<Piece> newSet) {
        newSet.add(new Piece(origPiece.getRow(), origPiece.getCol(), origPiece.getColor(), newSet));
    }
    
    public void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void removeFromSet() {
        pieceSet.remove(this);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
    
    public static Set<Piece> copyPieceSet(Set<Piece> origPieceSet) throws CloneNotSupportedException {
        Set<Piece> newPieceSet = new HashSet<>(origPieceSet.size());
        for (Piece p : origPieceSet) {
            Piece.copyIntoSet(p, newPieceSet);
        }
        return newPieceSet;
    }
    
    public Set<Move> generateCapturingMoves(State s, List<Piece> capturedList) {
        
        return null;
    }
    
    /**
     * Generate the set of legal non-jumping or single moves for this piece.
     * @param s The current state, which includes the necessary information for
     * this piece to generate all of its legal moves.
     * @return 
     */
    public Set<Move> generateSingleMoves(State s) {
        
        Piece[][] board = s.getBoard();
        
        Set<Move> moves = new HashSet<>();
        
        /*
        Variable dir determines the directionality of movement of this piece
        based on its color: for white pieces up and right are positive moves in terms
        in terms of the board's coordinates system, for black left and down are
        positive moves.
        */
        int dir = color.getDirection();
        
        /*
        Convert this piece's coordinates to relative coordinates, which allows us
        to check black and white pieces in the same way, regardless of their
        difference in directionality.
        */
        final int checkX = dir*col;
        final int checkY = dir*row;
        
        /*
        Construct non-jumping moves in relative coordinates.
        */
        final int relativeForward = checkY + 1;
        final int relativeLeft = checkX - 1;
        final int relativeRight = checkX + 1;

        /*
        For final movement, reconstruct non-jumping moves to absolute coordinates.
        We need these to actually update the board later, which only takes absolute
        coordinates.
        */
        final int absoluteForward = dir*relativeForward;
        final int absoluteLeft = dir*relativeLeft;
        final int absoluteRight = dir*relativeRight;

        /*
        Check if don't move off the board if move forward. This is for
        forward and diagonal moves.
        */
        if (relativeForward <= color.getBoardTopEdge()) {

            /*
            So now we know that we don't move off the board by moving
            forward.
            */

            // Check legality single left diagonal forward move
            if (relativeLeft >= color.getBoardLeftEdge()) {
                // Check for single left diagonal forward move
                if (board[absoluteForward][absoluteLeft] == null) {
                    moves.add(new SingleMove(absoluteLeft, absoluteForward, col, row));
                }

            }
            // Check legality single orthogonal forward move
            if (board[absoluteForward][col] == null) {
                moves.add(new SingleMove(col, absoluteForward, col, row));
            }


            // Check legality single right diagonal forward move
            if (relativeRight <= color.getBoardRightEdge()) {
                // Check if not occupied by other piece
                if (board[absoluteForward][absoluteRight] == null) {
                    moves.add(new SingleMove(absoluteRight, absoluteForward, col, row));
                }
            }
        }
        return moves;
    }

    
    public Constants.PlayerColors getColor() {
        return color;
    }
    
    public static Set<Piece> generatePieceSet(Constants.PlayerColors color, int size) {
        Set<Piece> pieceSet = new HashSet<>(size);
        for (int i = 0; i < size; i++)  {
            pieceSet.add(new Piece(0, 0, color, pieceSet));
        }
        return pieceSet;
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            result = 31 * result + (10000*row);
            result = 31 * result + (10000*col);
            hashCode = result;
        }
        return hashCode;
    }
    
    

    @Override
    public String toString() {
        return String.format("<%s,%s>", row,col);
    }
    
    public static void main(String[] args) {
        Piece p1 = new Piece(0, 0, Constants.PlayerColors.WHITE, null);
        Piece p2 = new Piece(8, 8, Constants.PlayerColors.BLACK, null);
        Piece p3 = new Piece(5, 5, Constants.PlayerColors.BLACK, null);
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p3.hashCode());
    }
    
}
