package dameo.players;

import dameo.Constants;
import dameo.util.DameoUtil;
import dameo.DameoEngine;
import dameo.Piece;
import dameo.gametree.State;
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
    public Move selectMove(State s) {
        Set<Move> moves = DameoEngine.generateLegalMoves(s);
        if (moves.isEmpty()) return null;
        
        boolean foundCaptureMove = false;
        Iterator<Move> it = moves.iterator();
        Move selectedMove = it.next();
        while (it.hasNext() && !foundCaptureMove) {
            selectedMove = it.next();
            if (selectedMove instanceof SingleCaptureMove) {
                foundCaptureMove = true;
            }
        }
        return selectedMove;
    }
    
}
