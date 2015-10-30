package dameo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class HumanPlayer extends Player {

    public HumanPlayer(Constants.PlayerColors color, Set<Piece> pieceSet) {
        super(color, pieceSet);
    }

    @Override
    public Move selectMove(List<Move> moves) {
        System.out.printf("Player %d, please enter a legal move: ", color);
        String s = Util.getConsoleInput();
        return new Move(s);
    }
    
    
    
}
