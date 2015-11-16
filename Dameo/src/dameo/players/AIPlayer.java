package dameo.players;

import dameo.Constants;
import dameo.Piece;
import dameo.gametree.State;
import dameo.move.Move;
import dameo.strategy.AIStrategy;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class AIPlayer extends Player {
    
    private AIStrategy strategy;

    public AIPlayer(Constants.PlayerColors color, Set<Piece> pieces, AIStrategy strategy) {
        super(color, pieces);
        this.strategy = strategy;
    }
    
    public void setStrategy(AIStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Move selectMove(State s) {
        return strategy.searchBestMove(s);
    }
    
}
