package dameo.move;

import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
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
    JumpNode root;

    public DeepestMultiJumpFinder() {
    }
    
    public List<Move> findDeepestNode(State state) {
        this.root = new JumpNode(null, state, 0, null, new ArrayList<>(), 0, 0);
        currentDeepest.add(root);
        currentDeepest.setCurrentMaxDepth(root.depth);
        
        /*
        Generate capturing moves for all pieces that can move in the current
        state. These will form the first level of the tree. All subsequent levels
        must be jumps with the same piece that was used in the ancestor in the
        first level of the tree.
        */
        for (Piece p : root.state.getCurrentPlayerPieces()) {
            List<SingleCaptureMove> captureMoves = p.generateCapturingMoves(root.state, new ArrayList<>());
            for (SingleCaptureMove m : captureMoves) {
                // Need to create a copy of root's list so nodes in other
                // branches don't modify it.
                List<Point> capturedListCopy = new ArrayList<>(root.capturedPieces);
                // We don't need to create a copy of the root node itself since we
                // don't modify it anyway.
                
                // Create child node to store generated move
                JumpNode child = new JumpNode(m, new State(root.state), root.depth+1, root,
                        capturedListCopy, m.oldX, m.oldY);
                // Recursively search child node
                recursiveFind(child);
            }
        }
        
        /*
        Construct multi-jump in the form of a stack from search tree.
        */
        return constructMultiMove();
    }
    
    /**
     * Construct a set of multi-jump moves from the list of all deepest nodes.
     * @return 
     */
    private List<Move> constructMultiMove() {
        // In fact we don't need the entire stack, just the deepest nodes, since
        // we only need the original and final location of the jumping piece and
        // the list of captured pieces and those are all stored in the nodes.
        List<Move> moves = new ArrayList<>();
        if (currentDeepest.currentMaxDepth > 0) {
            for (JumpNode n : currentDeepest) {
                moves.add(new MultiCaptureMove(n.captureMove.newX, n.captureMove.newY,
                        n.ancestorX, n.ancestorY, 0, n.capturedPieces));
            }
        }
        return moves;
    }
    
    private void recursiveFind(JumpNode n) {
        // Execute move stored in node n to advance state
        n.captureMove.mockExecute(n.state);
        // Add captured piece to captured piece list
        n.capturedPieces.add(new Point(n.captureMove.captX, n.captureMove.captY));
        
        final int x = n.captureMove.newX;
        final int y = n.captureMove.newY;
        Piece capturingPiece = n.state.getBoard()[y][x];
        List<SingleCaptureMove> moves = capturingPiece.generateCapturingMoves(n.state, n.capturedPieces);
        
        // This is a terminal node: no more jumps can be made
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
        else {
            for (SingleCaptureMove m : moves) {
                // Every child of n should get a new copy of the list of captured
                // pieces
                List<Point> cpl = new ArrayList<>(n.capturedPieces);
                // Create new child node for move m
                JumpNode child = new JumpNode(m, new State(n.state), n.depth+1,
                        n, cpl, n.ancestorX, n.ancestorY);
                // Recursively search this child
                recursiveFind(child);
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
    int ancestorX, ancestorY;

    public JumpNode(SingleCaptureMove captureMove, State state, int depth, JumpNode parent,
            List<Point> capturedPieces, int ancestorX, int ancestorY) {
        this.captureMove = captureMove;
        this.state = state;
        this.depth = depth;
        this.parent = parent;
        this.capturedPieces = capturedPieces;
        this.ancestorX = ancestorX;
        this.ancestorY = ancestorY;
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

    public void setCaptureMove(SingleCaptureMove captureMove) {
        this.captureMove = captureMove;
    }
    
}
