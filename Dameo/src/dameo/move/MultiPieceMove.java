package dameo.move;

import dameo.DameoEngine;
import dameo.gametree.State;
import java.util.List;

/**
 * Class encapsulating single or multi-piece movement.
 * @author Wim
 */
public class MultiPieceMove extends Move {
    
    List<SingleMove> moves;
    private int hashCode;

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
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            result = 31 * result + newX;
            result = 31 * result + newY;
            result = 31 * result + oldX;
            result = 31 * result + oldY;
        }
        return result;
    }
    
    

    @Override
    public String toString() {
        Move firstMove = moves.get(0);
        Move lastMove = moves.get(moves.size()-1);
        String prefix;
        if (moves.size() > 1) {
            prefix = "Multi-piece";
        }
        else {
            prefix = "Single-piece";
        }
        return String.format("%s: <%d,%d>:<%d,%d>", prefix, lastMove.oldX+1, lastMove.oldY+1,
                    firstMove.newX+1, firstMove.newY+1);
    }

    public List<SingleMove> getMoves() {
        return moves;
    }
    
}
