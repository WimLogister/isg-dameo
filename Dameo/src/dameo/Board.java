package dameo;

import java.util.Arrays;

/**
 *
 * @author Wim
 */
public class Board {
    
    private int[][] board = new int[8][8];

    public Board() {
        setupBoard();
    }
    
    /**
     * Initialize the Dameo game board.
     */
    private void setupBoard() {
        final int startingRanks = 3;
        for (int i=0; i < startingRanks; i++) {
            for (int j=i; j < board.length-i; j++) {
                board[i][j] = Constants.PLAYER_WHITE;
            }
        }
        
        for (int i=board.length-1; i > board.length-startingRanks-1; i--) {
            for (int j=i; j >= board.length-i-1; j--) {
                board[i][j] = Constants.PLAYER_BLACK;
            }
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
        Board b = new Board();
        System.out.println(b);
    }
    
}
