package dameo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class Board {
    
    /**
     * Set up and return a new Dameo game board array.
     * As a side effect, sets the coordinates of parameter pieces accordingly.
     * @param whitePieceSet
     * @param blackPieceSet
     * @return 
     */
    public static int[][] setupBoard(Set<Piece> whitePieceSet, Set<Piece> blackPieceSet) {
        final int[][] board = new int[8][8];
        
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
        Iterator<Piece> wit = whitePieceSet.iterator();
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
        Iterator<Piece> bit = blackPieceSet.iterator();
        // Set black piece coordinates
        while (bit.hasNext()) {
            bit.next().setCoords(positions[k][0], positions[k++][1]);
        }
        
        return board;
    }
    
    /**
     * Returns a string representation of the parameter board array.
     * @param board
     * @return 
     */
    public static String getBoardString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i=board.length-1; i >= 0; i--) {
            sb.append(String.format("(%d) ",i+1)).append(Arrays.toString(board[i])).append("\n");
        }
        sb.append("    ");
        for (int i = 0; i < board.length; i++) {
            sb.append(String.format("(%d)",i+1));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        Set<Piece> whitePieces = Piece.generatePieceSet(Constants.PlayerColors.WHITE, Constants.PIECES_PER_PLAYER);
        Set<Piece> blackPieces = Piece.generatePieceSet(Constants.PlayerColors.WHITE, Constants.PIECES_PER_PLAYER);
        int[][] board = Board.setupBoard(whitePieces, blackPieces);
        System.out.println("\n" + Board.getBoardString(board));
    }
    
}
