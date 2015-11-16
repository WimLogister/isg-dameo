package dameo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
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
    
    public static Piece copyIntoSet(Piece origPiece, Set<Piece> newSet) {
        return new Piece(origPiece.getRow(), origPiece.getCol(), origPiece.getColor(), newSet);
    }
    
    public void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void removeFromSet() {
        pieceSet.remove(this);
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
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

    @Override
    protected Piece clone() throws CloneNotSupportedException {
        return new Piece(row, col, color, pieceSet);
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
            result = 31 * result + (row);
            result = 31 * result + (col);
            result = 31 * result + color.ordinal();
            hashCode = result;
        }
        return hashCode;
    }
    
    

    @Override
    public String toString() {
        return String.format("<%s,%s>", row,col);
    }
    
    public static void main(String[] args) {
        Piece p1 = new Piece(4, 2, Constants.PlayerColors.WHITE, null);
        Piece p2 = new Piece(0, 8, Constants.PlayerColors.BLACK, null);
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
    
}
