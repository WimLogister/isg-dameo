package dameo.gametree;

import dameo.move.Move;

/**
 * Class encapsulating the notion of associating a move with the value of the
 * state that the move leads to.
 * @author Wim
 */
public class Edge {
    
    private final Move move;
    private final int value;

    public Edge(Move move, int value) {
        this.move = move;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Move getMove() {
        return move;
    }
    
    
    
}
