package dameo.move;

import dameo.gametree.State;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wim
 */
public class DeepestMultiJumpFinder {
    List<SingleCaptureMove> deepestNodesList = new ArrayList<>();
    
    public void findDeepestNode(JumpNode node, State state) {
        
    }
}

class JumpNode {
    Move captureMove;
    JumpNode parent;
    int depth;

    public JumpNode() {
    }

    public int getDepth() {
        return depth;
    }

    public Move getCaptureMove() {
        return captureMove;
    }

    public JumpNode getParent() {
        return parent;
    }

    public void setCaptureMove(Move captureMove) {
        this.captureMove = captureMove;
    }
    
    
}