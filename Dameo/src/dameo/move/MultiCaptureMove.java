package dameo.move;

import dameo.gametree.State;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class MultiCaptureMove extends Move {
    
    Stack<SingleCaptureMove> moves;

    public MultiCaptureMove(int newX, int newY, int oldX, int oldY) {
        super(newX, newY, oldX, oldY);
    }

    public MultiCaptureMove(Stack<SingleCaptureMove> moves) {
        super(0, 0, 0, 0);
        this.moves = moves;
    }
    
    
    
    

    @Override
    public void execute(State state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
