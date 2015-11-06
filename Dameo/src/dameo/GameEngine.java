package dameo;

import dameo.move.Move;
import dameo.move.SingleMove;
import dameo.players.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class GameEngine {
    
    private Board board;
    
    private Player p1;
    private Player p2;
    
    private Player currentPlayer;
    
    private boolean end;

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
        Set up players and set p1 as current player
        */
        setupPlayers(whitePieces, blackPieces);
        currentPlayer = p1;
        
        /*
        Initialize and set up the game board
        */
        board = new Board(whitePieces, blackPieces);
        
    }
    
    /**
     * Advance the game one turn.
     */
    private void next() {
        
        /*
        Generate set of legal moves
        */
        Set<Move> legalMoves = generateLegalSingleMoves();
        
        /*
        Current player selects move
        */
        Move m = currentPlayer.selectMove(legalMoves);
        if (m == null) end = true;
        else m.execute(board);
        
        /*
        Change player
        */
        if (currentPlayer == p1) {
            System.out.println("Tried to change to p2");
            currentPlayer = p2;
        }
        else if (currentPlayer == p2) {
            currentPlayer = p1;
        }
    }
    
    private void setupPlayers(Set<Piece> whitePieces, Set<Piece> blackPieces) {
        
        /*
        Determine player types from console input and set up players
        */
        int p1Type, p2Type;
        
        System.out.println("HUMAN = 1, AI = 2, RANDOM = 3\n" +
                "Please enter a player type for White player: ");
        p1Type = Integer.parseInt(DameoUtil.getConsoleInput());
        
        System.out.println("Please enter a player type for Black player: ");
        p2Type = Integer.parseInt(DameoUtil.getConsoleInput());
        
        p1 = Player.generatePlayer(Constants.PlayerTypes.getPlayerType(p1Type),
                Constants.PlayerColors.WHITE, whitePieces);
        p2 = Player.generatePlayer(Constants.PlayerTypes.getPlayerType(p2Type),
                Constants.PlayerColors.BLACK, blackPieces);
        
    }
    
    /**
     * Run an initialized game.
     */
    public void start() {
        while (!end) {
            System.out.println(board);
            DameoUtil.getConsoleInput();
            next();
        }
    }
    
    /**
     * Generate the list of legal moves in the current game state.
     * @return Hashset of legal moves in the current game state.
     */
    public static Set<Move> generateLegalMoves(Board board) {
        return null;
    }
    
    private Set<Move> generateLegalSingleMoves() {
        Set<Move> moves = new HashSet<>();
        
        // Generate white's legal single moves
        if (currentPlayer.getColor() == Constants.PlayerColors.WHITE) {
            
            for (Piece p : currentPlayer.getPieces()) {
                
                int y = p.getRow();
                int x = p.getCol();
                int forward = y + 1;
                
                // Check if don't move off the board if move forward
                if (forward <= 7) {
                    
                    int left = x - 1;
                    
                    // Check if left doesn't move off the board
                    if (left >= 0) {
                        // Check if not occupied by other piece
                        if (board.getBoard()[forward][left] == 0) {
                            moves.add(new SingleMove(p, left, forward));
                            // TODO: Check for multi-moves
                        }
                    }
                    
                    // Check if straight ahead is not occupied
                    if (board.getBoard()[forward][x] == 0) {
                        moves.add(new SingleMove(p, x, forward));
                        // TODO: check for multi-moves
                    }
                    
                    int right = x + 1;
                    
                    // Check if right doesn't move off the board
                    if (right <= 7) {
                        // Check if not occupied by other piece
                        if (board.getBoard()[forward][right] == 0) {
                            moves.add(new SingleMove(p, right, forward));
                            // TODO: check for multi-moves
                        }
                    }
                }
            }
        }
        else {
            for (Piece p : currentPlayer.getPieces()) {
                
                int y = p.getRow();
                int x = p.getCol();
                int forward = y - 1;
                
                // Check if don't move off the board if move forward
                if (forward >= 0) {
                    
                    int left = x + 1;
                    
                    // Check if left doesn't move off the board
                    if (left <= 7) {
                        // Check if not occupied by other piece
                        if (board.getBoard()[forward][left] == 0) {
                            moves.add(new SingleMove(p, left, forward));
                        }
                    }
                    
                    // Check if straight ahead is not occupied
                    if (board.getBoard()[forward][x] == 0) {
                        moves.add(new SingleMove(p, x, forward));
                    }
                    
                    int right = x - 1;
                    
                    // Check if right doesn't move off the board
                    if (right >= 0) {
                        // Check if not occupied by other piece
                        if (board.getBoard()[forward][right] == 0) {
                            moves.add(new SingleMove(p, right, forward));
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    /**
     * Execute parameter move.
     * This move needs to have already been checked and verified to be legal.
     * @param move 
     */
    private void executeMove(Move move) {
        // Move piece
        
    }

    public Board getBoard() {
        return board;
    }
    
    
    
    public static void main(String[] args) {
        GameEngine eng = new GameEngine();
        eng.init();
        eng.start();
        System.out.println(eng.getBoard());
        Set<Move> legalMoves = eng.generateLegalSingleMoves();
        System.out.println(legalMoves.size());
    }
    
    
}
