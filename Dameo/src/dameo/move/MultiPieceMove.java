package dameo.move;

import dameo.gametree.State;
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
        System.out.printf("Move %d pieces",moves.size());
        while (!moves.isEmpty()) {
            moves.remove(0).execute(state);
        }
    }
    
}
