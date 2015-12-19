package dameo.players;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.move.Move;
import dameo.Piece;
import dameo.util.DameoUtil;
import dameo.gametree.State;
import dameo.move.UndoMove;
import dameo.ui.FrameMove;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Wim
 */
public class HumanPlayer extends Player {
    
    private PlayerTypes type;

    public HumanPlayer(Constants.PlayerColors color, Set<Piece> pieceSet) {
        super(color, pieceSet);
    }

    @Override
    public Move selectMove(State s) {
        MoveSelectFrame frame = new MoveSelectFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        List<Move> moves = DameoEngine.generateLegalMoves(s);
        Move m = frame.getPlayerMoveInput(moves);
        return m;
    }
    
    public static void generateMoveFromString(String s) {
        
    }

    @Override
    public PlayerTypes getPlayerType() {
        return PlayerTypes.HUMAN;
    }
    
    class MoveSelectFrame extends JFrame {
        
        public MoveSelectFrame() throws HeadlessException {
        }
        
        Move getPlayerMoveInput(List<Move> moves) {
            List<FrameMove> moveList = new ArrayList<>(moves.size());
            for (Move m : moves) {
                moveList.add(new FrameMove(m));
            }
            UndoMove undoMove = new UndoMove();
            Collections.sort(moveList);
            moveList.add(0, new FrameMove(undoMove));
            Object[] possibilities = new Object[moveList.size()];
            int i = 0;
            for (FrameMove m : moveList) {
                possibilities[i++] = m.toString();
            }
            String s = (String)JOptionPane.showInputDialog(this, "Select move",
                    "Get player move", JOptionPane.QUESTION_MESSAGE, null,
                    possibilities, possibilities[0]);
            if (s.equals(undoMove.toString())) {
                return undoMove;
            }
            boolean moveFound = false;
            Iterator<Move> it = moves.iterator();
            Move returnMove = null;
            while (it.hasNext() && !moveFound) {
                Move m = it.next();
                if (m.toString().equals(s)) {
                    returnMove = m;
                    moveFound = true;
                }
            }
            return returnMove;
        }
    }
    
}
