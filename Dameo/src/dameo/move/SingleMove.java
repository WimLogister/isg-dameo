package dameo.move;

import dameo.Board;
import dameo.Constants;
import dameo.DameoUtil;
import dameo.Piece;
import dameo.players.Player;
import java.util.Iterator;
import java.util.Set;

/**
 * Single, non-jumping piece move
 * @author Wim
 */
public class SingleMove extends Move {

    public SingleMove(Piece piece, int newX, int newY) {
        super(piece, newX, newY);
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
    public void execute(int[][] board) {
        
        // Remove piece from previous position on board
        board[piece.getRow()][piece.getCol()] = 0;
        // Put piece on new position on board
        board[newY][newX] = piece.getColor().getValue();
        
        // Update piece information
        piece.setCoords(newY, newX);
    }


    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>", oldX+1,
                oldY+1, newX+1, newY+1);
    }
    
    
    
    
    public static void main(String[] args) {
        final int numPiece = Constants.PIECES_PER_PLAYER;
        Set<Piece> wp = Piece.generatePieceSet(Constants.PlayerColors.WHITE, numPiece);
        Set<Piece> bp = Piece.generatePieceSet(Constants.PlayerColors.BLACK, numPiece);
        int[][] board = Board.setupBoard(wp, bp);
        System.out.println(Board.getBoardString(board));
        int i = DameoUtil.getRandomIntFromTo(0, numPiece);
        Iterator<Piece> it = wp.iterator();
        Piece p = it.next();
        while (it.hasNext()) {
            p = it.next();
        }
        Move m = new SingleMove(p, 4, 4);
        m.execute(board);
        System.out.println(Board.getBoardString(board));
        
    }
    
}
