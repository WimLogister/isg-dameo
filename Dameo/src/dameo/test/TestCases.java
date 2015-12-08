package dameo.test;

import dameo.Constants;
import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;
import dameo.move.DeepestMultiJumpFinder;
import dameo.move.Move;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class TestCases {
    
    public static void createCoupTurc() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whiteKingPiece = new KingPiece(7, 0, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whiteKingPiece);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(5, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(6, 0, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece3 = new Piece(6, 2, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece4 = new Piece(5, 1, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece5 = new Piece(4, 1, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece6 = new Piece(4, 3, Constants.PlayerColors.BLACK, blackPieceSet);
        
        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        blackPieceSet.add(blackPiece3);
        blackPieceSet.add(blackPiece4);
        blackPieceSet.add(blackPiece5);
        blackPieceSet.add(blackPiece6);
        
        Piece[][] board = new Piece[8][8];
        board[whiteKingPiece.getRow()][whiteKingPiece.getCol()] = whiteKingPiece;
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        board[blackPiece4.getRow()][blackPiece4.getCol()] = blackPiece4;
        board[blackPiece5.getRow()][blackPiece5.getCol()] = blackPiece5;
        board[blackPiece6.getRow()][blackPiece6.getCol()] = blackPiece6;
        
        State s = new State(whitePieceSet, blackPieceSet, board);
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        Set<Move> moves = finder.findDeepestNode(s);
        Move m = moves.iterator().next();
        m.execute(s);
        System.out.println("debug");
    }
    
    public static void createNonPromotingBacklineCapture() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whitePiece = new Piece(5, 4, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whitePiece);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(6, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(7, 3, Constants.PlayerColors.BLACK, blackPieceSet);
//        Piece blackPiece3 = new Piece(6, 2, Constants.PlayerColors.BLACK, blackPieceSet);
        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
//        blackPieceSet.add(blackPiece3);
        
        Piece[][] board = new Piece[8][8];
        board[whitePiece.getRow()][whitePiece.getCol()] = whitePiece;
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
//        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        
        State s = new State(whitePieceSet, blackPieceSet, board);
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        Set<Move> moves = finder.findDeepestNode(s);
        Move m = moves.iterator().next();
        m.execute(s);
        System.out.println("debug");
    }

    public static void createCapturePromotionMove() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whitePiece = new Piece(5, 7, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whitePiece);
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(6, 7, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(7, 6, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece3 = new Piece(7, 3, Constants.PlayerColors.BLACK, blackPieceSet);
        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        blackPieceSet.add(blackPiece3);
        Piece[][] board = new Piece[8][8];
        board[whitePiece.getRow()][whitePiece.getCol()] = whitePiece;
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        State s = new State(whitePieceSet, blackPieceSet, board);
        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
        Set<Move> moves = finder.findDeepestNode(s);
        System.out.println("debug");
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

    public static void createKingMultiPieceState() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whiteKingPiece = new KingPiece(1, 6, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece = new Piece(0, 6, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whiteKingPiece);
        whitePieceSet.add(whitePiece);
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece[][] board = new Piece[8][8];
        board[whiteKingPiece.getRow()][whiteKingPiece.getCol()] = whiteKingPiece;
        board[whitePiece.getRow()][whitePiece.getCol()] = whitePiece;
        State s = new State(whitePieceSet, blackPieceSet, board);
        Set<Move> kingMoves = whiteKingPiece.generateSingleMoves(s);
        Set<Move> regularPieceMoves = whitePiece.generateSingleMoves(s);
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
    
    public static void main(String[] args) {
        createCoupTurc();
    }
    
}
