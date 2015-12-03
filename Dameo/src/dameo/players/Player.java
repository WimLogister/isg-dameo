package dameo.players;

import dameo.Constants;
import dameo.move.Move;
import dameo.Piece;
import dameo.gametree.NegaMax;
import dameo.gametree.State;
import java.util.Iterator;
import java.util.Set;

/**
 *
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
    
    public abstract Move selectMove(State s);
    
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
        if (type == PlayerTypes.DEBUG.value) {
            p = new DebugPlayer(color, pieceSet);
        }
        if (type == PlayerTypes.NEGAMAX.value) {
            p = new AIPlayer(color, pieceSet, new NegaMax(2, color));
        }
        return p;
    }
    
    public enum PlayerTypes {
        HUMAN(1), NEGAMAX(2), RANDOM(3), DEBUG(4);
        private int value;

        private PlayerTypes(int value) {
            this.value = value;
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
