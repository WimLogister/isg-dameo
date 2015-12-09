package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.move.Move;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wim
 */
public class IDNegamax extends NegaMax {
    
    private List<Edge> rootChildren;
    private int maxSearchDepth;

    public IDNegamax(int maxSearchDepth, Constants.PlayerColors color) {
        super(1, color);
        this.maxSearchDepth = maxSearchDepth;
    }

    @Override
    protected Collection<Move> negamaxMoveGeneration(State s, int depth) {
        /*
        During the first iteration of the algorithm in this turn, we have to
        generate a list of the root moves, which we will keep sorting during
        subsequent iterations.
        */
        if (searchDepth == 1 && depth == 0) {
            rootChildren = new ArrayList<>(DameoEngine.generateLegalMoves(s));
        }
        /*
        Sort root moves to improve search efficiency on next iteration.
        */
        if (searchDepth > 1 && depth == 0) {
            Collections.sort(rootChildren);
            List<Move> moves = new ArrayList<>(rootChildren.size());
            for (Edge child : rootChildren) {
                moves.add(child.getMove());
            }
            return moves;
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
        searchDepth = 1;
        Move m = null;
        while (searchDepth <= maxSearchDepth) {
            alphaBeta(s, 0, alpha, beta, 1).getMove();
            searchDepth++;
        }
        return m;
    }
    
}
