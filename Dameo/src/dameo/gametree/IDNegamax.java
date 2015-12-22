package dameo.gametree;

import dameo.util.Constants;
import dameo.move.Move;
import dameo.move.TimeOutMove;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class wrapping iterative deepening around a negamax algorithm with alpha-beta
 * pruning.
 * @author Wim
 */
public class IDNegamax extends NegaMax {
    
    private List<Move> rootChildren;
    public static int maxSearchDepth;

    public IDNegamax(int maxSearchDepth, Constants.PlayerColors color, long timeLimit) {
        super(1, color, timeLimit);
        IDNegamax.maxSearchDepth = maxSearchDepth;
    }

    /**
     * Takes care of move generation and ordering used by iterative deepening.
     * @param s
     * @param depth
     * @return 
     */
    @Override
    protected List<Move> negamaxMoveGeneration(State s, double depth) {
        /*
        During the first iteration of the algorithm in this turn, we have to
        generate a list of the root moves, which we will keep sorting during
        subsequent iterations.
        */
        if (iterationSearchDepth == 1 && depth == 0) {
            rootChildren = new ArrayList<>();
        }
        /*
        Sort root moves to improve search efficiency on next iteration.
        */
        if (iterationSearchDepth > 1 && depth == 0) {
            Collections.sort(rootChildren);
            List<Move> copyList = new ArrayList<>(rootChildren);
            rootChildren.clear();
            return copyList;
        }
        /*
        Internal node, return default view of moves.
        */
        else return super.negamaxMoveGeneration(s, depth);
    }

    /**
     * Add parameter move to the children of the root node so move ordering can
     * be applied later.
     * @param move 
     */
    @Override
    protected void disposeOfRootMove(Move move) {
        rootChildren.add(move);
    }
    
    
    @Override
    public Move searchBestMove(State s) {
        nodesExpanded = 0;
        iterationSearchDepth = 1;
        Move m = null;
        long start = System.currentTimeMillis();
        while (iterationSearchDepth < maxSearchDepth) {
            Move temp = alphaBeta(s, 0, alpha, beta, 1, start);
            if (!(temp instanceof TimeOutMove)) {
                m = temp;
            }
            System.out.printf("Best move value: %d\n",m.getValue());
            iterationSearchDepth++;
        }
        return m;
    }
    
}
