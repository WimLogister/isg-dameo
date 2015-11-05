package dameo.move;

import dameo.Board;
import dameo.Constants;
import dameo.DameoUtil;
import dameo.Piece;
import java.util.Iterator;
import java.util.Set;

/**
 * Single, non-jumping piece move
 * @author Wim
 */
public class SingleMove extends Move {
    private final int newX, newY;

    public SingleMove(Piece piece, int newX, int newY) {
        super(piece);
        this.newX = newX;
        this.newY = newY;
    }
    
    public Piece getPiece() {
        return piece;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    @Override
    public void execute(Board board) {
        
        int[][] b = board.getBoard();
        // Remove piece from previous position on board
        b[piece.getRow()][piece.getCol()] = 0;
        // Put piece on new position on board
        b[newY][newX] = piece.getColor().getValue();
        
        // Update piece information
        piece.setCoords(newY, newX);
    }

    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>", piece.getCol()+1,
                piece.getRow()+1, newX+1, newY+1);
    }
    
    
    
    
    public static void main(String[] args) {
        final int numPiece = Constants.PIECES_PER_PLAYER;
        Set<Piece> wp = Piece.generatePieceSet(Constants.PlayerColors.WHITE, numPiece);
        Set<Piece> bp = Piece.generatePieceSet(Constants.PlayerColors.BLACK, numPiece);
        Board board = new Board(wp, bp);
        System.out.println(board);
        int i = DameoUtil.getRandomIntFromTo(0, numPiece);
        Iterator<Piece> it = wp.iterator();
        Piece p = it.next();
        while (it.hasNext()) {
            p = it.next();
        }
        Move m = new SingleMove(p, 4, 4);
        m.execute(board);
        System.out.println(board);
        
    }
    
}
