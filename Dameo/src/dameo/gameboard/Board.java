package dameo.gameboard;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Class with some convenience methods regarding board creation and representation.
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
    public static Piece[][] setupBoard(Set<Piece> whitePieceSet, Set<Piece> blackPieceSet) {
        final Piece[][] board = new Piece[8][8];
        
        // Set up white pieces
        final int startingRanks = 3;
        Iterator<Piece> wIt = whitePieceSet.iterator();
        for (int i=0; i < startingRanks; i++) {
            for (int j=i; j < board.length-i; j++) {
                Piece whitePiece = wIt.next();
                board[i][j] = whitePiece;
                whitePiece.setCoords(i, j);
            }
        }
        
        // Set up black pieces
        Iterator<Piece> bIt = blackPieceSet.iterator();
        for (int i=board.length-1; i > board.length-startingRanks-1; i--) {
            for (int j=i; j >= board.length-i-1; j--) {
                Piece blackPiece = bIt.next();
                board[i][j] = blackPiece;
                blackPiece.setCoords(i, j);
            }
        }
        
        return board;
    }
    
    /**
     * Returns a string representation of the parameter board array.
     * @param board
     * @return 
     */
    public static String getBoardString(Piece[][] board) {
        StringBuilder sb = new StringBuilder();
        int[][] intBoard = new int[8][8];
        for (int i=board.length-1; i >= 0; i--) {
            for (int j=board[i].length-1; j >= 0; j--) {
                int value = 0;
                if (board[i][j] != null)
                    value = board[i][j].getBoardValue();
                intBoard[i][j] = value;
            }
        }
        for (int i=intBoard.length-1; i >= 0; i--) {
            sb.append(String.format("(%d) ",i+1)).append(Arrays.toString(intBoard[i])).append("\n");
        }
        sb.append("    ");
        for (int i = 0; i < intBoard.length; i++) {
            sb.append(String.format("(%d)",i+1));
        }
        return sb.toString();
    }
    
}
