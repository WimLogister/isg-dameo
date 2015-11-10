package dameo.players;

import dameo.Constants;
import dameo.move.Move;
import dameo.Piece;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wim
 */
public abstract class Player {
    
    protected final Constants.PlayerColors color;
    protected Set<Piece> pieces;
    
    protected Player(Constants.PlayerColors color, Set<Piece> pieces) {
        this.color = color;
        this.pieces = pieces;
    }
    
    public abstract Move selectMove(Set<Move> moves);
    
    /**
     * Static factory method for creating players based on passed parameters.
     * @param type The type of player (HUMAN == 1, AI == 2, RANDOM == 3)
     * @param color The color of the player (white or black)
     * @param pieceSet The set of pieces associated with this player
     * @return 
     */
    public static Player generatePlayer(Constants.PlayerTypes type, Constants.PlayerColors color, Set<Piece> pieceSet) {
        Player p = null;
        if (type == Constants.PlayerTypes.HUMAN) {
            p = new HumanPlayer(color, pieceSet);
        }
        if (type == Constants.PlayerTypes.RANDOM) {
            p = new RandomPlayer(color, pieceSet);
        }
        return p;
    }
    
    /**
     * Remove a piece from this player's pieces on the board.
     * @param p The piece to be removed from the board.
     */
    public void removePiece(Piece p) {
        pieces.remove(p);
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public Constants.PlayerColors getColor() {
        return color;
    }
    
    private class MovementConstraintChecker {
        
        private MovementConstraintChecker() {
            
        }
        
        private boolean checkForward() {
            
        }
        
    }
    
    
}
