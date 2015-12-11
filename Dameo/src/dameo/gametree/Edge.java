package dameo.gametree;

import dameo.move.Move;

/**
 * Class encapsulating the notion of associating a move with the value of the
 * state that the move leads to.
 * @author Wim
 */
public class Edge implements Comparable<Edge> {
    
    private final Move move;
    private final long value;

    public Edge(Move move, long value) {
        this.move = move;
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(Edge o) {
        if (this.value < o.value) {
            return -1;
        }
        if (this.value > o.value) {
            return 1;
        }
        return 0;
    }
    
    
    
}
