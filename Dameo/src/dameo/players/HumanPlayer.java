package dameo.players;

import dameo.Constants;
import dameo.move.Move;
import dameo.Piece;
import dameo.DameoUtil;
import dameo.gametree.State;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class HumanPlayer extends Player {

    public HumanPlayer(Constants.PlayerColors color, Set<Piece> pieceSet) {
        super(color, pieceSet);
    }

    @Override
    public Move selectMove(State s) {
        System.out.printf("Player %d, please enter a legal move: ", color);
        String str = DameoUtil.getConsoleInput();
        return null;
    }
    
    
    
}
