package dameo.move;

import dameo.Piece;
import dameo.gametree.State;

/**
 * Single, non-jumping piece move
 * @author Wim
 */
public class SingleMove extends Move {

    public SingleMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY);
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    @Override
    public void execute(State state) {
        Piece p = state.getBoard()[oldY][oldX];
        if (p == null) {
            System.out.println("Check for empty piece");
        }
        // Remove piece from previous position on board
        state.getBoard()[oldY][oldX] = null;
        // Put piece on new position on board
        state.getBoard()[newY][newX] = p;
        
        // Update piece information
        p.setCoords(newY, newX);
        
        super.promotePiece(state);
    }


    @Override
    public String toString() {
        return String.format("From:<%d,%d>, To:<%d,%d>", oldX+1,
                oldY+1, newX+1, newY+1);
    }
    
    
    
    
    public static void main(String[] args) {
//        final int numPiece = Constants.PIECES_PER_PLAYER;
//        Set<Piece> wp = Piece.generatePieceSet(Constants.PlayerColors.WHITE, numPiece);
//        Set<Piece> bp = Piece.generatePieceSet(Constants.PlayerColors.BLACK, numPiece);
//        int[][] board = Board.setupBoard(wp, bp);
//        System.out.println(Board.getBoardString(board));
//        int i = DameoUtil.getRandomIntFromTo(0, numPiece);
//        Iterator<Piece> it = wp.iterator();
//        Piece p = it.next();
//        while (it.hasNext()) {
//            p = it.next();
//        }
//        Move m = new SingleMove(p, 4, 4);
//        m.execute(board);
//        System.out.println(Board.getBoardString(board));
        
    }
    
}
