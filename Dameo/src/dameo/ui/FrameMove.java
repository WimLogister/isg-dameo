package dameo.ui;

import dameo.move.Move;

/**
 * Move class used to show moves in player move input list.
 * Implements comparable so moves can be ordered by coordinates.
 * @author Wim
 */
public class FrameMove implements Comparable<FrameMove> {
    
    Move move;

    public FrameMove(Move move) {
        this.move = move;
    }
    
    @Override
    public int compareTo(FrameMove o) {
        if (this.move.oldX < o.move.oldX) {
            return -1;
        }
        if (this.move.oldX == o.move.oldX) {
            if (this.move.oldY < o.move.oldY) {
                return -1;
            }
            if (this.move.oldY == o.move.oldY) {
                return 0;
            }
            return 1;
        }
        else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return move.toString();
    }
    
    
    
}
