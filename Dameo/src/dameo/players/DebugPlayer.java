package dameo.players;

import dameo.Constants;
import dameo.DameoUtil;
import dameo.Piece;
import dameo.move.Move;
import dameo.move.SingleCaptureMove;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class DebugPlayer extends Player {

    public DebugPlayer(Constants.PlayerColors color, Set<Piece> pieces) {
        super(color, pieces);
    }

    @Override
    public Move selectMove(Set<Move> moves) {
        int r = DameoUtil.getRandomIntFromTo(0, moves.size());
        int i = 0;
        boolean foundCaptureMove = false;
        Iterator<Move> it = moves.iterator();
        Move selectedMove = null;
        while (it.hasNext() && i++ <= r && !foundCaptureMove) {
            selectedMove = it.next();
            if (selectedMove instanceof SingleCaptureMove) {
                foundCaptureMove = true;
            }
        }
        return selectedMove;
    }
    
}
