package dameo.players;

import dameo.Constants;
import dameo.Piece;
import dameo.DameoUtil;
import dameo.move.DummyMove;
import dameo.move.Move;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A player that selects moves completely at random.
 * @author Wim
 */
public class RandomPlayer extends Player {

    public RandomPlayer(Constants.PlayerColors color, Set<Piece> pieces) {
        super(color, pieces);
    }

    /**
     * Random move selection.
     * @param moves Set of moves to choose from.
     * @return A move selected randomly from parameter set of moves.
     */
    @Override
    public Move selectMove(Set<Move> moves) {
        int r = DameoUtil.getRandomIntFromTo(0, moves.size());
        int i = 0;
        Iterator<Move> it = moves.iterator();
        Move selectedMove = null;
        while (it.hasNext() && i++ <= r) {
            selectedMove = it.next();
        }
        return selectedMove;
    }
    
}
