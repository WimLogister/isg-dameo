package dameo;

import dameo.gametree.State;
import dameo.move.Move;
import dameo.move.SingleCaptureMove;
import dameo.move.SingleMove;
import dameo.players.Player;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class GameEngine {
    
    private State currentState;
    
    private Player currentPlayer;
    private Player currentOpponent;
    
    private static boolean singletonExisting;
    private boolean end;
    
    private boolean printFlag;
    private static final String PRINT = "Y";
    
    private boolean debug = false;

    private GameEngine() {
        init();
    }
    
    private GameEngine(Player whitePlayer, Player blackPlayer) {
        currentPlayer = whitePlayer;
        currentOpponent = blackPlayer;
    }
    
    public static GameEngine createEngine() {
        if (!singletonExisting) return new GameEngine();
        else return null;
    }
    
    /**
     * Returns a GameEngine with a white negamax player and a black random player.
     * Implemented for testing basic negamax performance.
     * @return 
     */
    public static GameEngine createTestEngine() {
        Player whitePlayer = Player.generatePlayer(2, Constants.PlayerColors.WHITE,
                Piece.generatePieceSet(Constants.PlayerColors.WHITE, 18));
        Player blackPlayer = Player.generatePlayer(3, Constants.PlayerColors.BLACK,
                Piece.generatePieceSet(Constants.PlayerColors.BLACK, 18));
        return new GameEngine(whitePlayer, blackPlayer);
    }
    
    private void init() {
        
        if (currentPlayer == null) {
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
        }
        
        /*
        Initialize and set up the game board
        */
        Piece[][] board = Board.setupBoard(currentPlayer.getPieces(), currentOpponent.getPieces());
        
        currentState = new State(currentPlayer.getPieces(), currentOpponent.getPieces(), board);
        
        if (debug) {
            System.out.println("Enter Y for printing, anything else for no printing:");
            String flagString = DameoUtil.getConsoleInput();
            try {
                if (flagString.equalsIgnoreCase(PRINT)) {
                    printFlag = true;
                }
            } catch (IllegalArgumentException e) {
            }
        }
        
    }
    
    /**
     * Determine player types from console input and set up players with appropriate
     * colors and associate parameter piece sets with these players.
     * @param whitePieces The piece set for the white player.
     * @param blackPieces The piece set for the black player.
     */
    private void setupPlayers(Set<Piece> whitePieces, Set<Piece> blackPieces) {
        
        /*
        Determine player types from console input and set up players
        */
        int p1Type, p2Type;
        
        System.out.println("HUMAN = 1, NEGAMAX = 2, RANDOM = 3, DEBUG = 4\n" +
                "Please enter a player type for White player: ");
        p1Type = Integer.parseInt(DameoUtil.getConsoleInput());
        
        System.out.println("Please enter a player type for Black player: ");
        p2Type = Integer.parseInt(DameoUtil.getConsoleInput());
        
        currentPlayer = Player.generatePlayer(p1Type,
                Constants.PlayerColors.WHITE, whitePieces);
        currentOpponent = Player.generatePlayer(p2Type,
                Constants.PlayerColors.BLACK, blackPieces);
        
    }
    
    /**
     * Advance the game one turn.
     * The set of legal moves for the current player is generated. Then, the
     * current player is asked to select a move from this set. This move is then
     * executed and it becomes the next player's turn.
     */
    private void next() {
        
        /*
        Current player selects move
        */
        Move m = currentPlayer.selectMove(currentState);
        if (m == null) {
            end = true;
            if (debug) {
                System.out.printf("%s player has no more legal moves.\n", currentPlayer.getColor());
                System.out.printf("%s player wins.", currentOpponent.getColor());
            }
        }
        else {
            /*
            Execute move and change state
            */
            m.execute(currentState);
            if (printFlag) {
                System.out.println(m);
                System.out.println("State after move...");
                System.out.println(Board.getBoardString(currentState.getBoard()));
            }
        }
        
        /*
        Change player's pieces in current state
        */
        currentState = new State(currentState.getOpponentPieces(),
                currentState.getCurrentPlayerPieces(), currentState.getBoard());
        
        /*
        Change players
        */
        Player temp = currentPlayer;
        currentPlayer = currentOpponent;
        currentOpponent = temp;
    }
    
    /**
     * Run an initialized game.
     * @return The color of the winning player
     */
    public Constants.PlayerColors start() {
        while (!end) {
            if (printFlag)
                DameoUtil.getConsoleInput();
            next();
        }
        return currentPlayer.getColor();
    }
    
    public static Set<Move> generateLegalMoves(State s) {
        Set<Piece> currentPlayerPieceSet = s.getCurrentPlayerPieces();
        Set<Move> moveSet = new HashSet();
        for (Piece p : currentPlayerPieceSet) {
            moveSet.addAll(p.generateSingleMoves(s));
        }
        return moveSet;
    }
    
    /**
     * Generate the list of legal moves in the given game state.
     * Note: only returns legal moves for current player.
     * @return Hashset of legal moves in the current game state.
     */
    public static Set<Move> oldgenerateLegalMoves(State state) {
        
        Set<Piece> currentPlayerPieceSet = state.getCurrentPlayerPieces();
        
        Piece[][] board = state.getBoard();
        
        Set<Move> moves = new HashSet<>();
        
        Iterator<Piece> it = currentPlayerPieceSet.iterator();
        int dir = 0;
        Constants.PlayerColors color = null;
        if (it.hasNext()) {
            color = it.next().getColor();
            dir = color.getDirection();
        }
        boolean capturingMovesPresent = false;
        
        // Check for capturing moves
        for (Piece p : currentPlayerPieceSet) {
            
            final int y = p.getRow();
            final int x = p.getCol();
            final int checkX = dir*x;
            final int checkY = dir*y;
            final int relativeForward = checkY + 1;
            final int relativeLeft = checkX - 1;
            final int relativeRight = checkX + 1;

            final int absoluteForward = dir*relativeForward;
            final int absoluteLeft = dir*relativeLeft;
            final int absoluteRight = dir*relativeRight;
            
            /*
            Check if we don't move off the right side of the board. This is for
            right orthogonal moves.
            */
            if (relativeRight <= color.getBoardRightEdge()) {
                // Check for right orthogonal capturing move
                if (/* Check we don't move off the board */ relativeRight + 1 <= color.getBoardRightEdge() &&
                        board[y][absoluteRight] != null &&
                        /* Check for opponent's piece */ board[y][absoluteRight].getColor().getValue() == color.getOpponent()
                        /* Check for empty square */ && board[y][absoluteRight+dir] == null) {
                    moves.add(new SingleCaptureMove(absoluteRight+dir, y, x, y, absoluteRight, y));
                    capturingMovesPresent = true;
                }
            }
            
            /*
            Check for left orthogonal capturing move.
            */
            if (relativeLeft >= color.getBoardLeftEdge()) {
                    // Check for left orthogonal capturing move
                    if (/* Check we don't move off board*/ relativeLeft-1 >= color.getBoardLeftEdge()
                            /* Check for opponent piece */ && board[y][absoluteLeft] != null &&
                                    board[y][absoluteLeft].getColor().getValue() == color.getOpponent()
                            /* Check for empty square behind opponent */ && board[y][absoluteLeft-dir] == null) {
                        moves.add(new SingleCaptureMove(absoluteLeft-dir, y, x, y, absoluteLeft, y));
                        capturingMovesPresent = true;
                    }
            }
            
            /*
            Check for forward capturing move.
            */
            if (relativeForward <= color.getBoardTopEdge()) {
                // Check for forward capture
                if (board[absoluteForward][x] != null &&
                        board[absoluteForward][x].getColor().getValue() == color.getOpponent()) {
                        // Check if we don't end up jumping off the board and
                        // square behind enemy piece is empty
                        if (relativeForward + 1 <= color.getBoardTopEdge() && board[absoluteForward+dir][x] == null) {
                            moves.add(new SingleCaptureMove(x, absoluteForward+dir, x, y, x, absoluteForward));
                            capturingMovesPresent = true;
                        }
                }
            }
        }
        
        // If no capturing moves, check for non-capturing moves
        if (!capturingMovesPresent) {
            for (Piece p : currentPlayerPieceSet) {

                final int y = p.getRow();
                final int x = p.getCol();
                final int checkX = dir*x;
                final int checkY = dir*y;
                final int relativeForward = checkY + 1;
                final int relativeLeft = checkX - 1;
                final int relativeRight = checkX + 1;

                final int absoluteForward = dir*relativeForward;
                final int absoluteLeft = dir*relativeLeft;
                final int absoluteRight = dir*relativeRight;

                /*
                Check if don't move off the board if move forward. This is for
                forward and diagonal moves.
                */
                if (relativeForward <= color.getBoardTopEdge()) {

                    /*
                    So now we know that we don't move off the board by moving
                    forward.
                    */

                    // Check legality single left diagonal forward move
                    if (relativeLeft >= color.getBoardLeftEdge()) {
                        // Check for single left diagonal forward move
                        if (board[absoluteForward][absoluteLeft] == null) {
                            moves.add(new SingleMove(absoluteLeft, absoluteForward, x, y));
                        }

                    }
                    // Check legality single orthogonal forward move
                    if (board[absoluteForward][x] == null) {
                        moves.add(new SingleMove(x, absoluteForward, x, y));
                        // TODO: check for multi-moves
                    }


                    // Check legality single right diagonal forward move
                    if (relativeRight <= color.getBoardRightEdge()) {
                        // Check if not occupied by other piece
                        if (board[absoluteForward][absoluteRight] == null) {
                            moves.add(new SingleMove(absoluteRight, absoluteForward, x, y));
                        }
                    }
                }
            }
        }
        return moves;
    }

    public void setDEBUG(boolean DEBUG) {
        this.debug = DEBUG;
    }

    public State getCurrentState() {
        return currentState;
    }
    
    public static void testBoardCopy() {
        final int size = 18;
        Set<Piece> whiteSet = Piece.generatePieceSet(Constants.PlayerColors.WHITE, 18);
        Set<Piece> blackSet = Piece.generatePieceSet(Constants.PlayerColors.BLACK, 18);
        Piece[][] board = Board.setupBoard(whiteSet, blackSet);
        State s = new State(whiteSet, blackSet, board);
        System.out.println(Board.getBoardString(board));
        State copy = new State(s);
        System.out.println(Board.getBoardString(copy.getBoard()));
    }
    
    public static void runTestGames(int numRuns, boolean debug) {
        final int runs = 100;
        double[] values = new double[runs];
        for (int i = 0; i < runs; i++) {
            GameEngine eng = GameEngine.createTestEngine();
            eng.setDEBUG(debug);
            eng.init();
            Constants.PlayerColors color = eng.start();
            values[i] = color.getValue() % 2;
        }
        System.out.printf("Win percentage: %f", DameoUtil.mean(values));
    }
    
    public static void main(String[] args) {
        runTestGames(1, true);
    }
    
    
}
