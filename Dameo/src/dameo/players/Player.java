package dameo.players;

import dameo.Constants;
import dameo.move.Move;
import dameo.Piece;
import java.util.Iterator;
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
        return p;
    }
    
    public enum PlayerTypes {
        HUMAN(1), AI(2), RANDOM(3), DEBUG(4);
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
    
    public Piece findPiece(int x, int y) {
        Iterator<Piece> it = pieces.iterator();
        Piece p = null;
        while (it.hasNext()) {
            Piece match = it.next();
            if (match.getCol() == x && match.getRow() == y) {
                p = match;
            }
        }
        return p;
    }
    
}
