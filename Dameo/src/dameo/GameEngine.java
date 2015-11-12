package dameo;

import dameo.move.Move;
import dameo.move.SingleCaptureMove;
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
    
    private int[][] board;
    
    private Player currentPlayer;
    private Player currentOpponent;
    
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
        
        /*
        Initialize and set up the game board
        */
        board = Board.setupBoard(whitePieces, blackPieces);
        
    }
    
    /**
     * Advance the game one turn.
     * The set of legal moves for the current player is generated. Then, the
     * current player is asked to select a move from this set. This move is then
     * executed and it becomes the next player's turn.
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
        else {
            m.execute(board);
            
        }
        System.out.println(m);
        
        /*
        Change player
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
        
        System.out.println("HUMAN = 1, AI = 2, RANDOM = 3\n" +
                "Please enter a player type for White player: ");
        p1Type = Integer.parseInt(DameoUtil.getConsoleInput());
        
        System.out.println("Please enter a player type for Black player: ");
        p2Type = Integer.parseInt(DameoUtil.getConsoleInput());
        
        currentPlayer = Player.generatePlayer(Constants.PlayerTypes.getPlayerType(p1Type),
                Constants.PlayerColors.WHITE, whitePieces);
        currentOpponent = Player.generatePlayer(Constants.PlayerTypes.getPlayerType(p2Type),
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
    public static Set<Move> generateLegalMoves(Board board) {
        return null;
    }
    
    private Set<Move> generateLegalSingleMoves() {
        Set<Move> moves = new HashSet<>();
        
        Constants.PlayerColors color = currentPlayer.getColor();
        int dir = color.getDirection();
        
        for (Piece p : currentPlayer.getPieces()) {

            final int y = p.getRow();
            final int x = p.getCol();
            final int checkX = dir*x;
            final int checkY = dir*y;
            final int forward = checkY + 1;
            final int left = checkX - 1;
            final int right = checkX + 1;

            final int reconsForward = dir*forward;
            final int reconsLeft = dir*left;
            final int reconsRight = dir*right;

            /*
            Check if don't move off the board if move forward. This is for
            forward and diagonal moves.
            */
            if (forward <= color.getBoardTopEdge()) {

                /*
                So now we know that we don't move off the board by moving
                forward.
                */

                // Check if we don't move off the left side of the board
                if (left >= color.getBoardLeftEdge()) {
                    // Check for single left diagonal forward move
                    if (board[reconsForward][reconsLeft] == 0) {
                        moves.add(new SingleMove(p, reconsLeft, reconsForward));
                    }

                }
                // Check legality single orthogonal forward move
                if (board[reconsForward][x] == 0) {
                    moves.add(new SingleMove(p, x, reconsForward));
                    // TODO: check for multi-moves
                }

                // Check for forward capture
                if (board[reconsForward][x] == color.getOpponent()) {
                        // Check if we don't end up jumping off the board and
                        // square behind enemy piece is empty
                        if (reconsForward + 1 <= color.getBoardTopEdge() && board[reconsForward+1][x] == 0) {
                            moves.add(new SingleCaptureMove(x, reconsForward + 2, p, currentOpponent.findPiece(x, y)));
                        }
                }

                // Check legality single right diagonal forward move
                if (right <= color.getBoardRightEdge()) {
                    // Check if not occupied by other piece
                    if (board[reconsForward][reconsRight] == 0) {
                        moves.add(new SingleMove(p, reconsRight, reconsForward));
                    }
                }
            }
            /*
            Check if we don't move off the left side of the board. This is for
            left orthogonal moves.
            */
            if (left >= color.getBoardLeftEdge()) {
                    // Check for left orthogonal capturing move
                    if (left-1 >= color.getBoardLeftEdge() && board[y][reconsLeft] == color.getOpponent()) {
                        moves.add(new SingleCaptureMove(reconsLeft-1, y, currentOpponent.findPiece(reconsLeft, y), p));
                    }
            }

            /*
            Check if we don't move off the right side of the board. This is for
            right orthogonal moves.
            */
            if (right <= color.getBoardRightEdge()) {
                // Check for right orthogonal capturing move
                if (right + 1 <= color.getBoardRightEdge() && board[y][reconsRight] == color.getOpponent()) {
                    moves.add(new SingleCaptureMove(reconsRight+1, y, currentOpponent.findPiece(reconsRight, y), p));
                }
            }
        }
        return moves;
    }
    
    public int[][] getBoard() {
        return board;
    }

    
    public static void main(String[] args) {
        GameEngine eng = new GameEngine();
        eng.init();
        eng.start();
        Set<Move> legalMoves = eng.generateLegalSingleMoves();
        System.out.println(legalMoves.size());
    }
    
    
}
