package dameo.move;

import dameo.DameoEngine;
import dameo.gametree.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class MultiPieceMove extends Move {
    
    List<SingleMove> moves;

    public MultiPieceMove(List<SingleMove> moves) {
        super(0, 0, 0, 0);
        this.moves = moves;
    }

    @Override
    public void execute(State state) {
        if (DameoEngine.DEBUG > 2)
            System.out.printf("Move %d pieces\n",moves.size());
        for (int i = 0; i < moves.size(); i++) {
            moves.get(i).execute(state);
        }
    }
    
}
