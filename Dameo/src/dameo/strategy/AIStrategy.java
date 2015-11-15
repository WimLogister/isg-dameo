package dameo.strategy;

import dameo.gametree.State;
import dameo.move.Move;

/**
 *
 * @author Wim
 */
public interface AIStrategy {
    
    Move searchBestMove(State s);
    
}
