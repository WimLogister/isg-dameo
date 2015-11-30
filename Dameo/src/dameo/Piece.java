package dameo;

import dameo.gametree.State;
import dameo.move.Move;
import dameo.move.MultiPieceMove;
import dameo.move.SingleCaptureMove;
import dameo.move.SingleMove;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Models a regular (non-king) Dameo piece.
 * @author Wim
 */
public class Piece {
    
    private int row, col;
    private final Constants.PlayerColors color;
    private Set<Piece> pieceSet;
    private int hashCode;
    private int dir;

    private Piece(int row, int col, Constants.PlayerColors color, Set<Piece> pieceSet) {
        this.row = row;
        this.col = col;
        this.color = color;
        this.pieceSet = pieceSet;
        this.dir = color.getDirection();
    }
    
    protected Piece(Piece p) {
        this.row = p.getRow();
        this.col = p.getCol();
        this.color = p.getColor();
        this.pieceSet = p.getPieceSet();
        this.dir = p.getDir();
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

    public Set<Piece> getPieceSet() {
        return pieceSet;
    }

    public int getDir() {
        return dir;
    }
    
    public int getBoardValue() {
        return this.color.getValue();
    }
    
    public static Set<Piece> copyPieceSet(Set<Piece> origPieceSet) throws CloneNotSupportedException {
        Set<Piece> newPieceSet = new HashSet<>(origPieceSet.size());
        for (Piece p : origPieceSet) {
            Piece.copyIntoSet(p, newPieceSet);
        }
        return newPieceSet;
    }
    
    private static boolean listContainsPoint(List<Point> list, Point point) {
        boolean contains = false;
        for (Point listPoint : list) {
            if (listPoint.equals(point)) {
                contains = true;
            }
        }
        return contains;
    }
    
    public Set<SingleCaptureMove> generateCapturingMoves(State s, List<Point> capturedList) {
        
        Piece[][] board = s.getBoard();
        
        Set<SingleCaptureMove> moves = new HashSet<>();
        
        final int checkX = dir*col;
        final int checkY = dir*row;
        final int relativeForward = checkY + 1;
        final int relativeBackward = checkY - 1;
        final int relativeLeft = checkX - 1;
        final int relativeRight = checkX + 1;

        final int absoluteForward = dir*relativeForward;
        final int absoluteBackward = dir*relativeBackward;
        final int absoluteLeft = dir*relativeLeft;
        final int absoluteRight = dir*relativeRight;

        /*
        Check if we don't move off the right side of the board. This is for
        right orthogonal moves.
        */
        if (relativeRight <= color.getBoardRightEdge()) {
            if (    /* Check we don't move off the board */ relativeRight + 1 <= color.getBoardRightEdge() &&
                    /* Check there is something in square */ board[row][absoluteRight] != null &&
                    /* Check piece is opponent's */ board[row][absoluteRight].getColor().getValue() == color.getOpponent() &&
                    /* Check for empty square */ board[row][absoluteRight+dir] == null &&
                    /* Check piece has not already been captured in previous step in multi-jump */
                    !Piece.listContainsPoint(capturedList, new Point(absoluteRight, row))) {
                moves.add(new SingleCaptureMove(absoluteRight+dir, row, col, row, absoluteRight, row));
            }
        }

        /*
        Check for left orthogonal capturing move.
        */
        if (relativeLeft >= color.getBoardLeftEdge()) {
                if (    /* Check we don't move off board*/ relativeLeft-1 >= color.getBoardLeftEdge() &&
                        /* Check there is something in square */ board[row][absoluteLeft] != null &&
                        /* Check piece is opponent's */ board[row][absoluteLeft].getColor().getValue() == color.getOpponent() &&
                        /* Check for empty square behind opponent */ board[row][absoluteLeft-dir] == null &&
                        /* Check piece has not already been captured in previous step in multi-jump */
                        !Piece.listContainsPoint(capturedList, new Point(absoluteLeft, row))) {
                    
                    moves.add(new SingleCaptureMove(absoluteLeft-dir, row, col, row, absoluteLeft, row));
                }
        }

        /*
        Check for forward capturing move.
        */
        if (relativeForward <= color.getBoardTopEdge()) {
            if (    /* Check there is something in square */ board[absoluteForward][col] != null &&
                    /* Check piece is opponent's */ board[absoluteForward][col].getColor().getValue() == color.getOpponent()) {
                    
                    if (    /* Check if we don't end up jumping off the board */
                            relativeForward + 1 <= color.getBoardTopEdge()
                            /* Check empty square behind opponent */ && board[absoluteForward+dir][col] == null &&
                            /* Check piece has not already been captured in previous step in multi-jump */
                            !Piece.listContainsPoint(capturedList, new Point(col, absoluteForward))) {
                        moves.add(new SingleCaptureMove(col, absoluteForward+dir, col, row, col, absoluteForward));
                    }
            }
        }
        
        /*
        Check for backward capturing move.
        */
        if (relativeBackward >= color.getBoardBottomEdge()) {
            if (    /* Chek there is something in square */ board[absoluteBackward][col] != null &&
                    /* Check piece is opponent's */ board[absoluteBackward][col].getColor().getValue() == color.getOpponent()) {
                
                if (    /* Check if we don't end up jumping off the board */
                            relativeBackward - 1 >= color.getBoardBottomEdge()
                            /* Check empty square behind opponent */ && board[absoluteBackward-dir][col] == null &&
                            /* Check piece has not already been captured in previous step in multi-jump */
                            !Piece.listContainsPoint(capturedList, new Point(col, absoluteBackward))) {
                        moves.add(new SingleCaptureMove(col, absoluteBackward-dir, col, row, col, absoluteBackward));
                    }
            }
        }
        return moves;
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
        Convert this piece's coordinates to relative coordinates, which allows us
        to check black and white pieces in the same way, regardless of their
        difference in directionality.
        
        Variable dir determines the directionality of movement of this piece
        based on its color: for white pieces up and right are positive moves in terms
        in terms of the board's coordinates system, for black left and down are
        positive moves.
        */
        final int checkX = dir*col;
        final int checkY = dir*row;
        
        /*
        Construct non-jumping moves in relative coordinates.
        */
        final int relativeForward = checkY + 1;
        final int relativeBackward = checkY - 1;
        final int relativeLeft = checkX - 1;
        final int relativeRight = checkX + 1;

        /*
        For final movement, reconstruct non-jumping moves to absolute coordinates.
        We need these to actually update the board later, which only takes absolute
        coordinates.
        */
        final int absoluteForward = dir*relativeForward;
        final int absoluteBackward = dir*relativeBackward;
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
                    SingleMove firstMove = new SingleMove(absoluteLeft, absoluteForward, col, row);
                    
                    // See if you can move multiple pieces in the same direction
                    boolean canAddPieces = true;
                    int x_i = 0;
                    int y_i = 0;
                    
                    Stack<SingleMove> multiMove = new Stack();
                    multiMove.push(firstMove);
                    Stack<SingleMove> stackCopy = new Stack<>();
                    stackCopy.addAll(multiMove);
                    moves.add(new MultiPieceMove(stackCopy));
                    while (canAddPieces) {
                        // Check if still within bounds
                        if (relativeRight + x_i <= color.getBoardRightEdge() &&
                                relativeBackward - y_i >= color.getBoardBottomEdge()) {
                            // Check if it's not an empty square
                            if (board[absoluteBackward-dir*y_i][absoluteRight+dir*x_i] != null) {
                                // Check if own piece present
                                if (board[absoluteBackward-dir*y_i][absoluteRight+dir*x_i].getColor().getValue() == color.getValue()) {
                                    // Push new move onto stack
                                    multiMove.push(new SingleMove((absoluteRight+dir*x_i)-dir, (absoluteBackward-dir*y_i)+dir, absoluteRight+dir*x_i, absoluteBackward-dir*y_i));
                                    // Create new multi-piece move around copy of current stack
                                    Stack<SingleMove> copyStack = new Stack<>();
                                    copyStack.addAll(multiMove);
                                    moves.add(new MultiPieceMove(copyStack));
                                }
                                else {
                                    canAddPieces = false;
                                }
                            }
                            else {
                                canAddPieces = false;
                            }
                        }
                        else {
                            canAddPieces = false;
                        }
                        x_i++; y_i++;
                    }
                }
            }
            
            // Check legality single orthogonal forward move
            if (board[absoluteForward][col] == null) {
                SingleMove firstMove = new SingleMove(col, absoluteForward, col, row);
                
                // See if you can move multiple pieces in the same direction
                    boolean canAddPieces = true;
                    int y_i = 0;
                    
                    Stack<SingleMove> multiMove = new Stack();
                    multiMove.push(firstMove);
                    Stack<SingleMove> stackCopy = new Stack<>();
                    stackCopy.addAll(multiMove);
                    moves.add(new MultiPieceMove(stackCopy));
                    while (canAddPieces) {
                        // Check if still within bounds
                        if (relativeBackward - y_i >= color.getBoardBottomEdge()) {
                            // Check if this space is not empty
                            if (board[absoluteBackward-dir*y_i][col] != null) {
                                // Check if own piece present
                                if (board[absoluteBackward-dir*y_i][col].getColor().getValue() == color.getValue()) {
                                    /*
                                    Push new multimove on stack that includes this
                                    additional piece.
                                    */
                                    multiMove.push(new SingleMove(col, (absoluteBackward-dir*y_i)+dir, col, absoluteBackward-dir*y_i));
                                    // Create new multi-piece move around copy of current stack
                                    Stack<SingleMove> copyStack = new Stack<>();
                                    copyStack.addAll(multiMove);
                                    moves.add(new MultiPieceMove(copyStack));
                                }
                                else {
                                    canAddPieces = false;
                                }
                            }
                            else {
                                canAddPieces = false;
                            }
                        }
                        else {
                            canAddPieces = false;
                        }
                        y_i++;
                    }
            }


            // Check legality single right diagonal forward move
            if (relativeRight <= color.getBoardRightEdge()) {
                // Check if not occupied by other piece
                if (board[absoluteForward][absoluteRight] == null) {
                    SingleMove firstMove = new SingleMove(absoluteRight, absoluteForward, col, row);
                    
                    
                    // See if you can move multiple pieces in the same direction
                    boolean canAddPieces = true;
                    int x_i = 0;
                    int y_i = 0;
                    
                    Stack<SingleMove> multiMove = new Stack();
                    multiMove.push(firstMove);
                    Stack<SingleMove> stackCopy = new Stack<>();
                    stackCopy.addAll(multiMove);
                    moves.add(new MultiPieceMove(stackCopy));
                    while (canAddPieces) {
                        // Check if still within bounds
                        if (relativeLeft - x_i >= color.getBoardLeftEdge() &&
                                relativeBackward - y_i >= color.getBoardBottomEdge()) {
                            if (board[absoluteBackward-dir*y_i][absoluteLeft-dir*x_i] != null) {
                                // Check if own piece present
                                if (board[absoluteBackward-dir*y_i][absoluteLeft-dir*x_i].getColor().getValue() == color.getValue()) {
                                    multiMove.push(new SingleMove((absoluteLeft-dir*x_i)+dir, (absoluteBackward-dir*y_i)+dir, absoluteLeft-dir*x_i, absoluteBackward-dir*y_i));
                                    // Create new multi-piece move around copy of current stack
                                    Stack<SingleMove> copyStack = new Stack<>();
                                    copyStack.addAll(multiMove);
                                    moves.add(new MultiPieceMove(copyStack));
                                }
                                else {
                                    canAddPieces = false;
                                }
                            }
                            else {
                                canAddPieces = false;
                            }
                        }
                        else {
                            canAddPieces = false;
                        }
                        x_i++; y_i++;
                    }
                }
            }
        }
        return moves;
    }

    
    public Constants.PlayerColors getColor() {
        return color;
    }
    
    public static Set<Piece> generatePieceSet(Constants.PlayerColors color) {
        Set<Piece> pieceSet = new HashSet<>(Constants.PIECES_PER_PLAYER);
        for (int i = 0; i < Constants.PIECES_PER_PLAYER; i++)  {
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
    
    public static void hashCodeTest() {
        Piece p1 = new Piece(0, 0, Constants.PlayerColors.WHITE, null);
        Piece p2 = new Piece(8, 8, Constants.PlayerColors.BLACK, null);
        Piece p3 = new Piece(5, 5, Constants.PlayerColors.BLACK, null);
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p3.hashCode());
    }

    @Override
    public String toString() {
        return String.format("<%s,%s>", row,col);
    }
    
    public static void main(String[] args) {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(5, 10));
        pointList.add(new Point(7, 9));
        pointList.add(new Point(3, 1));
        
        System.out.println(Piece.listContainsPoint(pointList, new Point(7, 8)));
//        System.out.println(new Point(5, 10).equals(new Point(5, 10)));
    }
    
}
