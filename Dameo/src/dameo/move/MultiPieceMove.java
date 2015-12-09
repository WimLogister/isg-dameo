package dameo.move;

import dameo.DameoEngine;
import dameo.gametree.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Wim
 */
public class MultiPieceMove extends Move {
    
    List<SingleMove> moves;

    public MultiPieceMove(List<SingleMove> moves) {
        super(moves.get(0).newX, moves.get(0).newY, moves.get(moves.size()-1).oldX, moves.get(moves.size()-1).oldY, 0);
        this.moves = moves;
    }

    @Override
    public void execute(State state) {
        if (DameoEngine.DEBUG > 2)
            System.out.printf("Move %d pieces\n",moves.size());
        for (int i = 0; i < moves.size(); i++) {
            moves.get(i).execute(state);
        }
    }

    @Override
    public String toString() {
        Move firstMove = moves.get(0);
        Move lastMove = moves.get(moves.size()-1);
        return String.format("Multi-piece <%d,%d>:<%d,%d>", lastMove.oldX+1, lastMove.oldY+1,
                firstMove.newX+1, firstMove.newY+1);
    }

    @Override
    public int compareTo(Move o) {
        if (this.oldX < o.oldX) {
            return -1;
        }
        if (this.oldX == o.oldX) {
            if (this.oldY < o.oldY) {
                return -1;
            }
            if (this.oldY == o.oldY) {
                return 0;
            }
            return 1;
        }
        else {
            return 1;
        }
    }

    public List<SingleMove> getMoves() {
        return moves;
    }
    
}
