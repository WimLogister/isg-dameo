package dameo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class Board {
    
    private int[][] board = new int[8][8];

    /**
     * Create new Dameo game board and set it up with parameter pieces.
     * @param whitePieceSet The set of white draughts pieces
     * @param blackPieceSet The set of black draughts pieces
     */
    public Board(Set<Piece> whitePieceSet, Set<Piece> blackPieceSet) {
        setupBoard(whitePieceSet, blackPieceSet);
    }
    
    /**
     * Initialize the Dameo game board.
     * Parameters are the white and black piece sets, respectively.
     */
    private void setupBoard(Set<Piece> whitePieces, Set<Piece> blackPieces) {
        
        // Set up white pieces
        final int startingRanks = 3;
        int k = 0;
        // Container for positions of white pieces to be set by iterator later
        int[][] positions = new int[Constants.PIECES_PER_PLAYER][2];
        for (int i=0; i < startingRanks; i++) {
            for (int j=i; j < board.length-i; j++) {
                board[i][j] = Constants.PLAYER_WHITE;
                positions[k][0] = i;
                positions[k++][1] = j;
            }
        }
        
        k = 0;
        Iterator<Piece> wit = whitePieces.iterator();
        // Set white piece coordinates
        while (wit.hasNext()) {
            wit.next().setCoords(positions[k][0], positions[k++][1]);
        }
        
        k = 0;        
        // Set up black pieces
        // Container for positions of black pieces to be set by iterator later
        positions = new int[Constants.PIECES_PER_PLAYER][2];
        for (int i=board.length-1; i > board.length-startingRanks-1; i--) {
            for (int j=i; j >= board.length-i-1; j--) {
                board[i][j] = Constants.PLAYER_BLACK;
                positions[k][0] = i;
                positions[k++][1] = j;
            }
        }
        
        k = 0;
        Iterator<Piece> bit = blackPieces.iterator();
        // Set black piece coordinates
        while (bit.hasNext()) {
            bit.next().setCoords(positions[k][0], positions[k++][1]);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < board.length; i++) {
            sb.append(Arrays.toString(board[i])).append("\n");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        Set<Piece> whitePieces = Piece.generatePieceSet(
                Constants.PlayerColors.WHITE, Constants.PIECES_PER_PLAYER);
        Set<Piece> blackPieces = Piece.generatePieceSet(
                Constants.PlayerColors.WHITE, Constants.PIECES_PER_PLAYER);
        Board b = new Board(whitePieces, blackPieces);
        Iterator<Piece> it = whitePieces.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        it = blackPieces.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("\n" + b);
    }
    
}
