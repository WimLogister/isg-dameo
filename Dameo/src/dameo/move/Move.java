package dameo.move;

import dameo.Constants;
import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A move stores information about how the game board changes.
 * So a move stores the piece that is moved, its original position, its new
 * position and any pieces that were jumped over in between.
 * @author Wim
 */
public abstract class Move implements Comparable<Move> {
    
    public final int newX, newY, oldX, oldY;
    private long value;
    
    public Move(int newX, int newY, int oldX, int oldY, long value) {
        this.newX = newX;
        this.newY = newY;
        this.oldX = oldX;
        this.oldY = oldY;
        this.value = value;
    }
    
    public abstract void execute(State state);
    
    void promotePiece(State s) {
        Piece[][] board = s.getBoard();
        Piece p = board[newY][newX];
        // Check for white player
        if (p.getColor() == Constants.PlayerColors.WHITE) {
            // Check if last row reached
            if (newY == 7) {
                p.removeFromSet();
                Piece king = new KingPiece(p);
                board[newY][newX] = king;
                s.getCurrentPlayerPieces().add(king);
            }
        }
        else {
            // Check if last row reached
            if (newY == 0) {
                p.removeFromSet();
                Piece king = new KingPiece(p);
                board[newY][newX] = king;
                s.getCurrentPlayerPieces().add(king);
            }
        }
    }

    public long getValue() {
        return value;
    }
    
    

    public void setValue(long value) {
        this.value = value;
    }
    
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Move o) {
        if (this.value > o.getValue()) {
            return -1;
        }
        if (this.value < o.getValue()) {
            return 1;
        }
        return 0;
    }
    
    
    public static void main(String[] args) {
        Move m1 = new NullMove(500);
        Move m2 = new NullMove(10);
        List<Move> myList = new ArrayList<>();
        myList.add(m2); myList.add(m1);
        for (Move m : myList) {
            System.out.printf("Value: %s\n",m.getValue());
        }
        Collections.sort(myList);
        System.out.println();
        for (Move m : myList) {
            System.out.printf("Value: %s\n",m.getValue());
        }
    }
    
    
}
