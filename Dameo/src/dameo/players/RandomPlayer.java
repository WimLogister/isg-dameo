package dameo.players;

import dameo.Constants;
import dameo.Piece;
import dameo.util.DameoUtil;
import dameo.DameoEngine;
import dameo.gametree.State;
import dameo.move.Move;
import java.util.Iterator;
import java.util.Set;

/**
 * A player that selects moves completely at random.
 * @author Wim
 */
public class RandomPlayer extends Player {
    
    private PlayerTypes type;

    public RandomPlayer(Constants.PlayerColors color, Set<Piece> pieces) {
        super(color, pieces);
    }

    /**
     * Random move selection.
     * @param s Set of moves to choose from.
     * @return A move selected randomly from parameter set of moves.
     */
    @Override
    public Move selectMove(State s) {
        Set<Move> moves = DameoEngine.generateLegalMoves(s);
        int r = DameoUtil.getRandomIntFromTo(0, moves.size());
        int i = 0;
        Iterator<Move> it = moves.iterator();
        Move selectedMove = null;
        while (it.hasNext() && i++ <= r) {
            selectedMove = it.next();
        }
        return selectedMove;
    }

    @Override
    public PlayerTypes getPlayerType() {
        return PlayerTypes.RANDOM;
    }

}
