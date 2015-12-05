package dameo;

import dameo.util.DameoUtil;
import dameo.gametree.State;
import dameo.move.DeepestMultiJumpFinder;
import dameo.move.Move;
import dameo.move.SingleCaptureMove;
import dameo.move.SingleMove;
import dameo.players.Player;
import dameo.util.Observer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class DameoEngine {
    
    private State currentState;
    
    private Player currentPlayer;
    private Player currentOpponent;
    
    private static boolean singletonExisting;
    private boolean end;
    
    private boolean printFlag;
    private static final String PRINT = "Y";
    
    public static int DEBUG = 0;
    
    private int moveCounter;
    
    private List<Observer> observers = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    private DameoEngine() {
        init();
    }
    
    private DameoEngine(Player whitePlayer, Player blackPlayer) {
        currentPlayer = whitePlayer;
        currentOpponent = blackPlayer;
        init();
    }
    
    public static DameoEngine createEngine() {
        if (!singletonExisting) return new DameoEngine();
        else return null;
    }
    
    /**
     * Returns a GameEngine with a white negamax player and a black random player.
     * Implemented for testing basic negamax performance.
     * @return 
     */
    public static DameoEngine createTestEngine() {
        Player whitePlayer = Player.generatePlayer(2, Constants.PlayerColors.WHITE,
                Piece.generatePieceSet(Constants.PlayerColors.WHITE));
        Player blackPlayer = Player.generatePlayer(3, Constants.PlayerColors.BLACK,
                Piece.generatePieceSet(Constants.PlayerColors.BLACK));
        return new DameoEngine(whitePlayer, blackPlayer);
    }
    
    public static DameoEngine createRandomPlayerGame() {
        Player whitePlayer = Player.generatePlayer(3, Constants.PlayerColors.WHITE,
                Piece.generatePieceSet(Constants.PlayerColors.WHITE));
        Player blackPlayer = Player.generatePlayer(3, Constants.PlayerColors.BLACK,
                Piece.generatePieceSet(Constants.PlayerColors.BLACK));
        return new DameoEngine(whitePlayer, blackPlayer);
    }
    
    public static DameoEngine createInputPlayerGame(Player.PlayerTypes p1type,
            Player.PlayerTypes p2type) {
        Player whitePlayer = Player.generatePlayer(p1type.getValue(), Constants.PlayerColors.WHITE,
                Piece.generatePieceSet(Constants.PlayerColors.WHITE));
        Player blackPlayer = Player.generatePlayer(p2type.getValue(), Constants.PlayerColors.BLACK,
                Piece.generatePieceSet(Constants.PlayerColors.BLACK));
        return new DameoEngine(whitePlayer, blackPlayer);
    }
    
    private void init() {
        players.add(currentPlayer);
        players.add(currentOpponent);
        
        if (currentPlayer == null) {
            /*
            Create black and white piece sets
            */
            Set<Piece> blackPieces = Piece.generatePieceSet(Constants.PlayerColors.BLACK);
            Set<Piece> whitePieces = Piece.generatePieceSet(Constants.PlayerColors.WHITE);
            
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
        
        if (DEBUG > 0) {
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
    
    public void setObserver(Observer o) {
        this.observers.add(o);
    }
    
    /**
     * Advance the game one turn.
     * The set of legal moves for the current player is generated. Then, the
     * current player is asked to select a move from this set. This move is then
     * executed and it becomes the next player's turn.
     */
    private void next() {

        if (DEBUG > 0) {
            System.out.printf("%s player to move\n",currentPlayer.getColor());
            System.out.printf("Turn number %d\n",moveCounter++);
        }
        
        /*
        Current player selects move
        */
        Move m = currentPlayer.selectMove(currentState);
        if (m == null) {
            end = true;
            if (DEBUG > 0) {
                System.out.printf("%s player has no more legal moves.\n", currentPlayer.getColor());
                System.out.printf("%s player wins.", currentOpponent.getColor());
            }
        }
        else {
            /*
            Execute move and change state
            */
            m.execute(currentState);
            if (DEBUG > 0) {
                System.out.println(m.getClass().toString());
                
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
    
    public static void createDebugState() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whiteKingPiece = new KingPiece(7, 5, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whiteKingPiece);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(5, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(2, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        
        Piece[][] board = new Piece[8][8];
        board[whiteKingPiece.getRow()][whiteKingPiece.getCol()] = whiteKingPiece;
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        
        State s = new State(whitePieceSet, blackPieceSet, board);
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        Set<Move> moves = finder.findDeepestNode(s);
        System.out.println("debug");
    }
    
    public static void createKingDebutState() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whiteKingPiece = new KingPiece(5, 7, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whiteKingPiece);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(5, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(6, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece3 = new Piece(4, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece4 = new Piece(6, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece5 = new Piece(7, 2, Constants.PlayerColors.BLACK, blackPieceSet);
        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        blackPieceSet.add(blackPiece3);
        blackPieceSet.add(blackPiece4);
        blackPieceSet.add(blackPiece5);
        
        Piece[][] board = new Piece[8][8];
        board[whiteKingPiece.getRow()][whiteKingPiece.getCol()] = whiteKingPiece;
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        board[blackPiece4.getRow()][blackPiece4.getCol()] = blackPiece4;
        board[blackPiece5.getRow()][blackPiece5.getCol()] = blackPiece5;
        
        State s = new State(whitePieceSet, blackPieceSet, board);
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        Set<Move> moves = finder.findDeepestNode(s);
        System.out.println("debug");
    }
    
    /**
     * Run an initialized game.
     * @return The color of the winning player
     */
    public Constants.PlayerColors start() {
        while (!end) {
            if (DEBUG > 0)
//                DameoUtil.getConsoleInput();
            next();
            for (Observer o : observers) {
                o.update();
            }
        }
        return currentPlayer.getColor();
    }
    
    /**
     * Generates all legal moves for a given game state.
     * @param s
     * @return 
     */
    public static Set<Move> generateLegalMoves(State s) {
        Set<Piece> currentPlayerPieceSet = s.getCurrentPlayerPieces();
        Set<Move> moveSet = new HashSet();
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        Set<Move> jumpMoves = finder.findDeepestNode(s);
        /*
        The player must play jump moves if there are any. If there are none,
        he can play any non-jumping move.
        */
        if (jumpMoves.isEmpty()) {
            for (Piece p : currentPlayerPieceSet) {
                moveSet.addAll(p.generateSingleMoves(s));
            }
        }
        else moveSet = jumpMoves;
        return moveSet;
    }

    public List<Player> getPlayers() {
        return players;
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

    public State getCurrentState() {
        return currentState;
    }
    
    public static void runTestGames(int numRuns) {
        DameoEngine.DEBUG = 0;
        double[] values = new double[numRuns];
        for (int i = 0; i < numRuns; i++) {
            DameoEngine eng = DameoEngine.createTestEngine();
            System.out.printf("Now starting game number %d\n",i+1);
            eng.init();
            Constants.PlayerColors color = eng.start();
            values[i] = color.getValue() % 2;
        }
        System.out.printf("Win percentage: %f", DameoUtil.mean(values));
    }
    
    
    
    public static void main(String[] args) {
        DameoEngine.createKingDebutState();
    }
    
    
}
