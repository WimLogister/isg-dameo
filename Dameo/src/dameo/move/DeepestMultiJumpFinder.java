package dameo.move;

import dameo.Piece;
import dameo.gametree.State;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class DeepestMultiJumpFinder {
    MultiJumpList currentDeepest = new MultiJumpList();
    Stack<Move> multiJump;

    public DeepestMultiJumpFinder() {
    }
    
    
    
    public Stack<Move> findDeepestNode(JumpNode root, State state) {
        currentDeepest.add(root);
        for (Piece p : root.state.getCurrentPlayerPieces()) {
            Set<SingleCaptureMove> captureMoves = p.generateCapturingMoves(root.state, new ArrayList<>());
            for (SingleCaptureMove m : captureMoves) {
                // Need to create a copy of root's list so nodes in other
                // branches don't modify it.
                List<Point> capturedListCopy = new ArrayList<>(root.capturedPieces);
                // Create child node around generated move
                JumpNode child = new JumpNode(m, new State(root.state), root.depth+1, root,
                        capturedListCopy);
                // Recursively search child node
                recursiveFind(child);
            }
        }
        /*
        Construct multi-jump in the form of a stack from search tree.
        */
        JumpNode node = currentDeepest;
        while (node != null) {
            multiJump.push(node.captureMove);
            node = node.parent;
        }
        return multiJump;
    }
    
    private void recursiveFind(JumpNode n) {
        // Execute move stored in node n to advance state
        n.captureMove.execute(n.state);
        // Add captured piece to captured piece list
        n.capturedPieces.add(new Point(n.captureMove.captX, n.captureMove.captY));
        
        final int x = n.captureMove.newX;
        final int y = n.captureMove.newY;
        Piece capturingPiece = n.state.getBoard()[y][x];
        Set<SingleCaptureMove> moves = capturingPiece.generateCapturingMoves(n.state, null);
        if (moves.isEmpty()) {
            /*
            Case: we've found a series of jumps that is strictly longer than
            the longest we'd found before. Need to clear out all previous moves
            and save this series of moves.
            */
            if (n.depth > currentDeepest.getCurrentMaxDepth()) {
                // Clear list of current deepest paths
                currentDeepest = new MultiJumpList(n.depth);
                // Add current node/path to list of multi-jumps
                currentDeepest.add(n);
            }
            /*
            Case: the series of multi-jumps represented by the current node is equal
            in length to an existing series. Need to add this one since a player
            may choose in case of multiple series of jumps of equal length.
            */
            else if (n.depth == currentDeepest.getCurrentMaxDepth()) {
                currentDeepest.add(n);
            }
        }
    }
}


class MultiJumpList extends ArrayList<JumpNode> {
    int currentMaxDepth;

    public MultiJumpList(int currentMaxDepth) {
        this.currentMaxDepth = currentMaxDepth;
    }

    public MultiJumpList() {
    }

    public int getCurrentMaxDepth() {
        return currentMaxDepth;
    }

    public void setCurrentMaxDepth(int currentMaxDepth) {
        this.currentMaxDepth = currentMaxDepth;
    }
    
    
}

class JumpNode {
    SingleCaptureMove captureMove;
    /*
    State is the current state, on which the capture move has not yet been performed.
    */
    State state;
    int depth;
    JumpNode parent;
    List<Point> capturedPieces;

    public JumpNode(Move captureMove, State state, int depth, JumpNode parent,
            List<Point> capturedPieces) {
        this.captureMove = captureMove;
        this.state = state;
        this.depth = depth;
        this.parent = parent;
        this.capturedPieces = capturedPieces;
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