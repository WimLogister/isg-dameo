package dameo.gameboard;

import dameo.util.Constants;
import dameo.util.DameoUtil;
import dameo.gametree.State;
import dameo.move.DeepestMultiJumpFinder;
import dameo.move.Move;
import dameo.move.UndoMove;
import dameo.players.Player;
import dameo.util.Observer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * The game engine: coordinates setting up the board and players, advancing turns, etc.
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
    
    public static int moveCounter;
    
    private List<Observer> observers = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    private Stack<State> stateStack;
    
    private double branchingFactor;
    
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
    
    /**
     * Initialize the game. Players must have been set up properly.
     */
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
        
        stateStack = new Stack<>();
        
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
        
        branchingFactor += generateLegalMoves(currentState).size();
        moveCounter++;
        
        if (DEBUG > 0) {
            System.out.printf("%s player to move\n",currentPlayer.getColor());
            System.out.printf("Turn number %d\n",moveCounter);
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
        else if (m instanceof UndoMove) {
            if (stateStack.size() >= 2) {
                stateStack.pop();
                currentState = stateStack.pop();
                /*
                Change players
                */
                moveCounter--;
            }
        }
        else {
            
            stateStack.push(new State(currentState));
            /*
            Execute move and change state
            */
            m.execute(currentState);
            if (DEBUG > 1) {
                System.out.println(m.getClass().toString());
                
                System.out.println("State after move...");
                System.out.println(Board.getBoardString(currentState.getBoard()));
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
        
        
    }
    
    /**
     * Run an initialized game.
     * @return The color of the winning player
     */
    public Constants.PlayerColors start() {
        while (!end) {
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
    public static List<Move> generateLegalMoves(State s) {
        Set<Piece> currentPlayerPieceSet = s.getCurrentPlayerPieces();
        List<Move> moveSet = new ArrayList<>();
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        List<Move> jumpMoves = finder.findDeepestNode(s);
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
    
    public State getCurrentState() {
        return currentState;
    }

    public double getBranchingFactor() {
        return branchingFactor;
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
    
    public static void branchingFactorTest(int numRuns) throws IOException {
        DameoEngine.DEBUG = 0;
        double[] possibleMoves = new double[numRuns];
        double[] movesPlayed = new double[numRuns];
        for (int i = 0; i < numRuns; i++) {
            DameoEngine eng = DameoEngine.createTestEngine();
            DameoEngine.moveCounter = 0;
            System.out.printf("Now starting game %d\n",i+1);
            eng.init();
            eng.start();
            possibleMoves[i] = eng.getBranchingFactor();
            movesPlayed[i] = DameoEngine.moveCounter;
        }
        File branchingFile = new File("branching_factors.txt");
        FileWriter fw = new FileWriter(branchingFile);
        BufferedWriter bw = new BufferedWriter(fw);
        
        for (int i = 0; i < possibleMoves.length; i++) {
            bw.write(String.format("%f", possibleMoves[i]/movesPlayed[i]));
            bw.newLine();
        }
        bw.close();
        
        File moveFile = new File("num_of_moves.txt");
        FileWriter fw1 = new FileWriter(moveFile);
        BufferedWriter bw1 = new BufferedWriter(fw1);
        
        for (int i = 0; i < possibleMoves.length; i++) {
            bw1.write(String.format("%f", possibleMoves[i]/movesPlayed[i]));
            bw1.newLine();
        }
        bw1.close();
    }
    
}
