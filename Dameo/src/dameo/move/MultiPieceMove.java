package dameo.move;

import dameo.gametree.State;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class MultiPieceMove extends Move {
    
    Stack<SingleMove> moves;

    public MultiPieceMove(Stack<SingleMove> moves, int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY);
        this.moves = moves;
    }
    
    @Override
    public void execute(State state) {
        while (!moves.empty()) {
            moves.pop().execute(state);
        }
    }
    
}
