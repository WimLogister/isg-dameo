package dameo.gametree;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.evalfunction.CompositeEvaluator;
import dameo.move.Move;
import dameo.strategy.AIStrategy;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class NegaMax implements AIStrategy {
    
    private final CompositeEvaluator evaluator;
    private final long alpha, beta;
    private final int searchDepth;
    private final int negamaxColor;

    public NegaMax(int searchDepth, Constants.PlayerColors color) {
        this.evaluator = CompositeEvaluator.createFullEvaluator(color);
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        this.searchDepth = searchDepth;
        this.negamaxColor = color.getNegamaxColor();
    }
    
    private Edge alphaBeta(State s, int depth, long alpha, long beta, int color) {
        long score = Integer.MIN_VALUE;
//        if (s.getCurrentPlayerPieces().size() == 1) {
//            System.out.println("debug");
//        }
        Set<Move> moves = DameoEngine.generateLegalMoves(s);
        Edge bestMove = null;
        
        // No more moves, game is lost
        if (moves.isEmpty()) {
            bestMove = new Edge(null, color*Integer.MIN_VALUE);
//            // This player has no more moves: this negamax player loses
//            if (color == negamaxColor) {
//                bestMove = new Edge(null, Integer.MIN_VALUE);
//            }
//            // Opponent has no more moves: this negamax player wins
//            else {
//                bestMove = new Edge(null, Integer.MAX_VALUE);
//            }
        }
        else if (depth == 0) {
            bestMove = new Edge(null, color*evaluator.evaluate(s));
        }
        else {
            for (Move m : moves) {
                State copyState = new State(s);
                m.execute(copyState);
                copyState.switchPlayers();
                Edge valueNode = alphaBeta(copyState, depth-1, -beta, -alpha, -color);
                if (-valueNode.getValue() > score) {
                    bestMove = new Edge(m, -valueNode.getValue());
                    score = -valueNode.getValue();
                }
                if (score > alpha) alpha = score;
                if (score >= beta) break;
            }
        }
        if (bestMove == null) {
            bestMove = new Edge(moves.iterator().next(), color*Integer.MIN_VALUE);
        }
        return bestMove;
    }
    
    @Override
    public Move searchBestMove(State s) {
        Move m = alphaBeta(s, searchDepth, alpha, beta, 1).getMove();
        return m;
    }
    
}
