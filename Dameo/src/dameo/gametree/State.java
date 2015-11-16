package dameo.gametree;

import dameo.Board;
import dameo.GameEngine;
import dameo.Piece;
import dameo.players.Player;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class State {
    private final Set<Piece> currentPlayerPieces;
    private final Set<Piece> opponentPieces;
    private final int[][] board;

    /**
     * Regular constructor for State encapsulating a particular game state.
     * @param currentPlayerPieces
     * @param opponentPieces
     * @param board 
     */
    public State(Set<Piece> currentPlayerPieces, Set<Piece> opponentPieces, int[][] board) {
        this.currentPlayerPieces = currentPlayerPieces;
        this.opponentPieces = opponentPieces;
        this.board = board;
    }
    
    /**
     * Copy constructor for an instance of a State encapsulating a particular game state.
     * @param oldState The state that is to be copied.
     */
    public State(State oldState) {
        this.currentPlayerPieces = new HashSet<>(oldState.currentPlayerPieces.size());
        for (Piece p : oldState.currentPlayerPieces) {
           Piece.copyIntoSet(p, this.currentPlayerPieces);
        }
        this.opponentPieces = new HashSet<>(oldState.opponentPieces.size());
        for (Piece p : oldState.opponentPieces) {
            Piece.copyIntoSet(p, this.opponentPieces);
        }
        this.board = Board.copyBoard(oldState.board);
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
