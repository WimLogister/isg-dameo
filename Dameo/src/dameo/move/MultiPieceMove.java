package dameo.move;

import dameo.gametree.State;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class MultiPieceMove extends Move {
    
    Stack<SingleMove> moves;

    public MultiPieceMove(Stack<SingleMove> moves) {
        super(0, 0, 0, 0);
        this.moves = moves;
    }
    
    @Override
    public void execute(State state) {
        System.out.printf("Move %d pieces",moves.size());
        if (moves.size() > 1) {
            System.out.println("Debug");
        }
        while (!moves.empty()) {
            moves.pop().execute(state);
        }
    }
    
}
