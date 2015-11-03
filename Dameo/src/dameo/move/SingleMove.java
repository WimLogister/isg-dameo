package dameo.move;

import dameo.Board;
import dameo.Constants;
import dameo.Piece;

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
        b[piece.getCol()][piece.getRow()] = 0;
        // Put piece on new position on board
        b[newY][newX] = piece.getColor().getValue();
        
        // Update piece information
        piece.setCoords(newY, newX);
    }
    
    public static void main(String[] args) {
        Board board = new Board(Piece.generatePieceSet(Constants.PlayerColors.WHITE, Constants.PIECES_PER_PLAYER),
                Piece.generatePieceSet(Constants.PlayerColors.BLACK, Constants.PIECES_PER_PLAYER));
    }
    
}
