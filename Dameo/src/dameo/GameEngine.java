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
    
    private int[][] board;
    private State currentState;
    
    private Player currentPlayer;
    private Player currentOpponent;
    
    private static boolean singletonExisting;
    private boolean end;

    private GameEngine() {
        init();
    }
    
    public static GameEngine createEngine() {
        if (!singletonExisting) return new GameEngine();
        else return null;
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
        
        /*
        Initialize and set up the game board
        */
        board = Board.setupBoard(whitePieces, blackPieces);
        
        currentState = new State(whitePieces, blackPieces, board);
        
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
            System.out.printf("%s player has no more legal moves.\n", currentPlayer.getColor());
            System.out.printf("%s player wins.", currentOpponent.getColor());
        }
        else {
            /*
            Execute move and change state
            */
            m.execute(currentState);
            System.out.println(m);
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
     * Run an initialized game.
     */
    public void start() {
        while (!end) {
            System.out.println(Board.getBoardString(board));
            DameoUtil.getConsoleInput();
            next();
        }
    }
    
    
    /**
     * Generate the list of legal moves in the current game state.
     * @return Hashset of legal moves in the current game state.
     */
    public static Set<Move> generateLegalMoves(State state) {
        
        Set<Piece> currentPlayerPieceSet = state.getCurrentPlayerPieces();
        Set<Piece> opponentPieceSet = state.getOpponentPieces();
        
        int[][] board = state.getBoard();
        
        Set<Move> moves = new HashSet<>();
        
        Iterator<Piece> it = currentPlayerPieceSet.iterator();
        Constants.PlayerColors color = it.next().getColor();
        int dir = color.getDirection();
        
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

                // Check if we don't move off the left side of the board
                if (relativeLeft >= color.getBoardLeftEdge()) {
                    // Check for single left diagonal forward move
                    if (board[absoluteForward][absoluteLeft] == 0) {
                        moves.add(new SingleMove(p, absoluteLeft, absoluteForward));
                    }

                }
                // Check legality single orthogonal forward move
                if (board[absoluteForward][x] == 0) {
                    moves.add(new SingleMove(p, x, absoluteForward));
                    // TODO: check for multi-moves
                }

                // Check for forward capture
                if (board[absoluteForward][x] == color.getOpponent()) {
                        // Check if we don't end up jumping off the board and
                        // square behind enemy piece is empty
                        if (relativeForward + 1 <= color.getBoardTopEdge() && board[absoluteForward+dir][x] == 0) {
                            Piece opPiece = Piece.findPiece(opponentPieceSet, x, absoluteForward);
                        moves.add(new SingleCaptureMove(x, absoluteForward + dir, p, opPiece));
                        }
                }

                // Check legality single right diagonal forward move
                if (relativeRight <= color.getBoardRightEdge()) {
                    // Check if not occupied by other piece
                    if (board[absoluteForward][absoluteRight] == 0) {
                        moves.add(new SingleMove(p, absoluteRight, absoluteForward));
                    }
                }
            }
            /*
            Check if we don't move off the left side of the board. This is for
            left orthogonal moves.
            */
            if (relativeLeft >= color.getBoardLeftEdge()) {
                    // Check for left orthogonal capturing move
                    if (/* Check we don't move off board*/ relativeLeft-1 >= color.getBoardLeftEdge()
                            /* Check for opponent piece */ && board[y][absoluteLeft] == color.getOpponent()
                            /* Check for empty square behind opponent */ && board[y][absoluteLeft-dir] == 0) {
                        Piece opPiece = Piece.findPiece(opponentPieceSet, absoluteLeft, y);
                        moves.add(new SingleCaptureMove(absoluteLeft-dir, y, p, opPiece));
                    }
            }

            /*
            Check if we don't move off the right side of the board. This is for
            right orthogonal moves.
            */
            if (relativeRight <= color.getBoardRightEdge()) {
                // Check for right orthogonal capturing move
                if (/* Check we don't move off the board */ relativeRight + 1 <= color.getBoardRightEdge()
                        /* Check for opponent's piece */ && board[y][absoluteRight] == color.getOpponent()
                        /* Check for empty square */ && board[y][absoluteRight+dir] == 0) {
                    Piece opPiece = Piece.findPiece(opponentPieceSet, absoluteRight, y);
                    moves.add(new SingleCaptureMove(absoluteRight+dir, y, p, opPiece));
                }
            }
        }
        return moves;
    }
    
    public int[][] getBoard() {
        return board;
    }

    public static void testBoardCopy() {
        final int size = 18;
        int[][] board = Board.setupBoard(Piece.generatePieceSet(Constants.PlayerColors.WHITE, size),
                Piece.generatePieceSet(Constants.PlayerColors.BLACK, size));
        System.out.println(Board.getBoardString(board));
        int[][] newBoard = Board.copyBoard(board);
        System.out.println(Board.getBoardString(newBoard));
    }
    
    public static void runGame() {
        GameEngine eng = new GameEngine();
        eng.start();
    }
    
    public static void main(String[] args) {
        runGame();
    }
    
    
}
