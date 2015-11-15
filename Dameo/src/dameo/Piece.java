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
    
    
    
    public static Piece copyPiece(Piece origPiece) {
        return new Piece(origPiece.getRow(), origPiece., Constants.PlayerColors.WHITE, null)
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
    public String toString() {
        return String.format("<%s,%s>", row,col);
    }
    
}
