package dameo.players;

import dameo.Constants;
import dameo.move.Move;
import dameo.Piece;
import dameo.gametree.IDNegamax;
import dameo.gametree.State;
import java.util.Set;

/**
 * Abstract class encapsulating any type of player.
 * @author Wim
 */
public abstract class Player {
    
    protected final Constants.PlayerColors color;
    protected Set<Piece> pieces;
    
    public static final long DEBUG_ALPHA = Long.MIN_VALUE;
    public static final long DEBUG_BETA = Long.MAX_VALUE;
    
    protected Player(Constants.PlayerColors color, Set<Piece> pieces) {
        this.color = color;
        this.pieces = pieces;
    }
    
    /**
     * Abstract method that encapsulates the notion of selecting the best move for
     * a given game state. Is implemented by human, AI and random players.
     * @param s
     * @return 
     */
    public abstract Move selectMove(State s);
    
    public abstract PlayerTypes getPlayerType();
    
    /**
     * Static factory method for creating players based on passed parameters.
     * @param type The type of player (HUMAN == 1, AI == 2, RANDOM == 3)
     * @param color The color of the player (white or black)
     * @param pieceSet The set of pieces associated with this player
     * @return 
     */
    public static Player generatePlayer(int type, Constants.PlayerColors color, Set<Piece> pieceSet) {
        Player p = null;
        if (type == PlayerTypes.HUMAN.value) {
            p = new HumanPlayer(color, pieceSet);
        }
        if (type == PlayerTypes.RANDOM.value) {
            p = new RandomPlayer(color, pieceSet);
        }
        if (type == PlayerTypes.NEGAMAX.value) {
            p = new AIPlayer(color, pieceSet, new IDNegamax(20, color, 5));
        }
        return p;
    }
    
    public enum PlayerTypes {
        HUMAN(1), NEGAMAX(2), RANDOM(3);
        private int value;

        private PlayerTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
        
        
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
    
    
}
