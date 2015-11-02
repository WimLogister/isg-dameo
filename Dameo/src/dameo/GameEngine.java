package dameo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class GameEngine {
    
    private Board board;
    
    private Player p1;
    private Player p2;

    public GameEngine() {
    }
    
    private void init() {
        
        /*
        Create black and white piece sets
        */
        Set<Piece> blackPieces = Piece.generatePieceSet(Constants.PlayerColors.BLACK,
                Constants.PIECES_PER_PLAYER);
        Set<Piece> whitePieces = Piece.generatePieceSet(Constants.PlayerColors.WHITE,
                Constants.PIECES_PER_PLAYER);
        
        
        /*
        Determine player types from console input
        */
        int p1Type, p2Type;
        
        System.out.println("HUMAN = 1, AI = 2\n" +
                "Please enter a player type for White player: ");
        p1Type = Integer.parseInt(Util.getConsoleInput());
        
        System.out.println("Please enter a player type for Black player: ");
        p2Type = Integer.parseInt(Util.getConsoleInput());
        
        p1 = Player.generatePlayer(Constants.PlayerTypes.getPlayerType(p1Type),
                Constants.PlayerColors.WHITE, whitePieces);
        p2 = Player.generatePlayer(Constants.PlayerTypes.getPlayerType(p2Type),
                Constants.PlayerColors.BLACK, blackPieces);
        
        /*
        Initialize and set up the game board
        */
        
        board = new Board(whitePieces, blackPieces);
        
        
    }
    
    /**
     * Advance the game one turn.
     */
    private void next() {
        
    }
    
    private void setupPlayers() {
        
    }
    
    /**
     * Generate the list of legal moves in the current game state.
     * @return Hashset of legal moves in the current game state.
     */
    private Set<Move> getLegalMoves() {
        
    }
    
    /**
     * Execute parameter move.
     * This move has already been checked and verified to be legal.
     * @param move 
     */
    private void executeMove(Move move) {
        // Move piece
        
    }
    
    private static 
    
}
