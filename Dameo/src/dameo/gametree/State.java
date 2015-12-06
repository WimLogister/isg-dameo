package dameo.gametree;

import dameo.KingPiece;
import dameo.Piece;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class State {
    private Set<Piece> currentPlayerPieces;
    private Set<Piece> opponentPieces;
    private final Piece[][] board;

    /**
     * Regular constructor for State encapsulating a particular game state.
     * @param currentPlayerPieces
     * @param opponentPieces
     * @param board 
     */
    public State(Set<Piece> currentPlayerPieces, Set<Piece> opponentPieces, Piece[][] board) {
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
            if (p instanceof KingPiece) {
                KingPiece.copyIntoSet(p, this.currentPlayerPieces);
            }
            else {
                Piece.copyIntoSet(p, this.currentPlayerPieces);
            }
        }
        this.opponentPieces = new HashSet<>(oldState.opponentPieces.size());
        for (Piece p : oldState.opponentPieces) {
            if (p instanceof KingPiece) {
                KingPiece.copyIntoSet(p, this.opponentPieces);
            }
            else {
                Piece.copyIntoSet(p, this.opponentPieces); 
            }
        }
        this.board = createBoardCopy(currentPlayerPieces, opponentPieces);
        
    }
    
    private static Piece[][] createBoardCopy(Set<Piece> currentPlayerSet, Set<Piece> opponentSet) {
        Piece[][] newBoard = new Piece[8][8];
        
        for (Piece p : currentPlayerSet) {
            newBoard[p.getRow()][p.getCol()] = p;
        }
        
        for (Piece p : opponentSet) {
            newBoard[p.getRow()][p.getCol()] = p;
        }
        
        return newBoard;
    }

    public Set<Piece> getCurrentPlayerPieces() {
        return currentPlayerPieces;
    }

    public Set<Piece> getOpponentPieces() {
        return opponentPieces;
    }
    
    public Piece[][] getBoard() {
        return board;
    }
    
    public void switchPlayers() {
        Set<Piece> temp = this.currentPlayerPieces;
        this.currentPlayerPieces = this.opponentPieces;
        this.opponentPieces = temp;
    }
    
    private static Piece[][] putPiecesOnNewBoard(Set<Piece> currentPlayerPieces,
            Set<Piece> opponentPieces) {
        Piece[][] newBoard = new Piece[8][8];
        for (Piece p : currentPlayerPieces) {
            newBoard[p.getRow()][p.getCol()] = p;
        }
        for (Piece p : opponentPieces) {
            newBoard[p.getRow()][p.getCol()] = p;
        }
        return newBoard;
    }
}
