package dameo;

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
    
    public abstract Move selectMove(List<Move> moves);
    
    public static Player generatePlayer(Constants.PlayerTypes type, Constants.PlayerColors color, Set<Piece> pieceSet) {
        Player p = null;
        if (type == Constants.PlayerTypes.HUMAN) {
            p = new HumanPlayer(color, pieceSet);
        }
        return p;
    }
}
