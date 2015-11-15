package dameo.gametree;

import dameo.Piece;
import dameo.players.Player;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class State {
    private Set<Piece> currentPlayerPieces;
    private Set<Piece> opponentPieces;
    private int[][] board;

    public State(Set<Piece> currentPlayerPieces, Set<Piece> opponentPieces, int[][] board) {
        this.currentPlayerPieces = currentPlayerPieces;
        this.opponentPieces = opponentPieces;
        this.board = board;
    }

    public Set<Piece> getCurrentPlayerPieces() {
        return currentPlayerPieces;
    }

    public Set<Piece> getOpponentPieces() {
        return opponentPieces;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
}
