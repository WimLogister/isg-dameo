package dameo;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class Piece {
    
    private int row, col;
    private final Constants.PlayerColors color;

    private Piece(int row, int col, Constants.PlayerColors color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
    
    public void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
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

    public Constants.PlayerColors getColor() {
        return color;
    }
    
    public static Set<Piece> generatePieceSet(Constants.PlayerColors color, int size) {
        Set<Piece> pieceSet = new HashSet<>(size);
        for (int i = 0; i < size; i++)  {
            pieceSet.add(new Piece(0, 0, color));
        }
        return pieceSet;
    }

    @Override
    public String toString() {
        return String.format("<%s,%s>", row,col);
    }
    
    
    
    
    
}
