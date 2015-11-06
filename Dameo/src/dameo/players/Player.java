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

    public Set<Piece> getPieces() {
        return pieces;
    }

    public Constants.PlayerColors getColor() {
        return color;
    }
    
    
}
