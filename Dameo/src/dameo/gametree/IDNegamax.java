package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.move.Move;
import dameo.move.NullMove;
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

    public IDNegamax(int maxSearchDepth, Constants.PlayerColors color) {
        super(1, color);
        IDNegamax.maxSearchDepth = maxSearchDepth;
    }

    @Override
    protected List<Move> negamaxMoveGeneration(State s, int depth) {
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
            if (nullMoveFound) {
                System.out.println("debug");
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
        while (iterationSearchDepth <= maxSearchDepth) {
            System.out.printf("Searching at depth %d\n", iterationSearchDepth);
            m = alphaBeta(s, 0, alpha, beta, 1);
            iterationSearchDepth+=2;
        }
        System.out.printf("Nodes expanded: %d\n",nodesExpanded);
        return m;
    }
    
}
