package dameo.gametree;

import dameo.Constants;
import dameo.GameEngine;
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
        this.alpha = Long.MIN_VALUE;
        this.beta = Long.MAX_VALUE;
        this.searchDepth = searchDepth;
        this.negamaxColor = color.getNegamaxColor();
    }
    
    private Edge alphaBeta(State s, int depth, long alpha, long beta, int color) {
        long score = Long.MIN_VALUE;
        Set<Move> moves = GameEngine.generateLegalMoves(s);
        Edge bestMove = null;
        
        // No more moves, game is lost
        if (moves.isEmpty()) {
            bestMove = new Edge(null, Long.MIN_VALUE);
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
            System.out.println("debug");
        }
        return bestMove;
    }
    
    @Override
    public Move searchBestMove(State s) {
        Move m = alphaBeta(s, searchDepth, alpha, beta, negamaxColor).getMove();
        return m;
    }
    
}
