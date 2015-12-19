package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.move.Move;
import dameo.move.NullMove;
import dameo.move.TimeOutMove;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wim
 */
public class IDNegamax extends NegaMax {
    
    private List<Move> rootChildren;
    public static int maxSearchDepth;
    private final double timeLimit;

    public IDNegamax(int maxSearchDepth, Constants.PlayerColors color, double timeLimit) {
        super(1, color);
        IDNegamax.maxSearchDepth = maxSearchDepth;
        this.timeLimit = timeLimit;
    }

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
            boolean nullMoveFound = false;
            for (Move m : rootChildren) {
                if (m instanceof NullMove) {
                    nullMoveFound = true;
                }
            }
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

    @Override
    protected void disposeOfRootMove(Move move) {
        rootChildren.add(move);
    }
    
    
    @Override
    public Move searchBestMove(State s) {
        nodesExpanded = 0;
        iterationSearchDepth = 1;
        Move m = null;
//        int dynamicSearchDepth = maxSearchDepth;
//        if (DameoEngine.moveCounter < 5) {
//            dynamicSearchDepth -= 2;
//        }
        long start = System.currentTimeMillis();
        while (iterationSearchDepth < maxSearchDepth) {
            System.out.printf("Searching at depth %d\n", iterationSearchDepth);
            Move temp = alphaBeta(s, 0, alpha, beta, 1, 0, start);
            if (!(temp instanceof TimeOutMove)) {
                m = temp;
            }
            System.out.printf("Best move value: %d\n",m.getValue());
            iterationSearchDepth++;
//            System.out.printf("Deepest forced line search depth: %d\n", this.getHighestForcedSearchDepth());
        }
//        System.out.printf("Nodes expanded: %d\n",nodesExpanded);
        return m;
    }
    
}
