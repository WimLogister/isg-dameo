package dameo.test;

import dameo.Constants;
import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;
import dameo.move.DeepestMultiJumpFinder;
import dameo.move.Move;
import dameo.players.Player;
import java.util.HashSet;
import java.util.Set;

/**
 * Some static methods used for debugging.
 * @author Wim
 */
public class TestCases {
    
    public static void createRegularVSIDNegamax() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whitePiece1 = new Piece(4, 5, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece2 = new Piece(3, 4, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece3 = new Piece(3, 5, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece4 = new Piece(0, 7, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whitePiece1);
        whitePieceSet.add(whitePiece2);
        whitePieceSet.add(whitePiece3);
        whitePieceSet.add(whitePiece4);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(7, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(6, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece3 = new Piece(6, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece4 = new Piece(5, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece5 = new Piece(5, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece6 = new Piece(5, 6, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece7 = new Piece(4, 3, Constants.PlayerColors.BLACK, blackPieceSet);
        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        blackPieceSet.add(blackPiece3);
        blackPieceSet.add(blackPiece4);
        blackPieceSet.add(blackPiece5);
        blackPieceSet.add(blackPiece6);
        blackPieceSet.add(blackPiece7);
        
        Piece[][] board = new Piece[8][8];
        
        board[whitePiece1.getRow()][whitePiece1.getCol()] = whitePiece1;
        board[whitePiece2.getRow()][whitePiece2.getCol()] = whitePiece2;
        board[whitePiece3.getRow()][whitePiece3.getCol()] = whitePiece3;
        board[whitePiece4.getRow()][whitePiece4.getCol()] = whitePiece4;
        
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        board[blackPiece4.getRow()][blackPiece4.getCol()] = blackPiece4;
        board[blackPiece5.getRow()][blackPiece5.getCol()] = blackPiece5;
        board[blackPiece6.getRow()][blackPiece6.getCol()] = blackPiece6;
        board[blackPiece7.getRow()][blackPiece7.getCol()] = blackPiece7;
        
        State s = new State(blackPieceSet, whitePieceSet, board);
        Player negamax = Player.generatePlayer(Player.PlayerTypes.NEGAMAX.getValue(), Constants.PlayerColors.BLACK, blackPieceSet);
        Move m = negamax.selectMove(s);
        int a = 5;
    }
    
    public static void createIDNullMoveTestCase2() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whitePiece1 = new Piece(0, 2, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece2 = new Piece(0, 3, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece3 = new Piece(0, 4, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece kingPiece = new KingPiece(1, 0, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whitePiece1);
        whitePieceSet.add(whitePiece2);
        whitePieceSet.add(whitePiece3);
        whitePieceSet.add(kingPiece);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(1, 7, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(2, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece3 = new Piece(3, 3, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece4 = new Piece(3, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece5 = new Piece(4, 5, Constants.PlayerColors.BLACK, blackPieceSet);

        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        blackPieceSet.add(blackPiece3);
        blackPieceSet.add(blackPiece4);
        blackPieceSet.add(blackPiece5);
        
        Piece[][] board = new Piece[8][8];
        board[whitePiece1.getRow()][whitePiece1.getCol()] = whitePiece1;
        board[whitePiece2.getRow()][whitePiece2.getCol()] = whitePiece2;
        board[whitePiece3.getRow()][whitePiece3.getCol()] = whitePiece3;
        board[kingPiece.getRow()][kingPiece.getCol()] = kingPiece;
        
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        board[blackPiece4.getRow()][blackPiece4.getCol()] = blackPiece4;
        board[blackPiece5.getRow()][blackPiece5.getCol()] = blackPiece5;
        
        State s = new State(blackPieceSet, whitePieceSet, board);
        Player negamax = Player.generatePlayer(Player.PlayerTypes.NEGAMAX.getValue(), Constants.PlayerColors.BLACK, blackPieceSet);
        Move m = negamax.selectMove(s);
        
//        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
//        Set<Move> moves = finder.findDeepestNode(s);
//        Move m = moves.iterator().next();
//        m.execute(s);
        System.out.println("debug");
    }
    public static void createIDNullMoveTestCase() {
        Set<Piece> whitePieceSet = new HashSet<>();
        Piece whitePiece1 = new Piece(0, 0, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece2 = new Piece(0, 1, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece3 = new Piece(0, 2, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece4 = new Piece(0, 7, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece5 = new Piece(1, 2, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece6 = new Piece(1, 3, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece7 = new Piece(1, 4, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece8 = new Piece(1, 5, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece9 = new Piece(1, 6, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece10 = new Piece(2, 2, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece11 = new Piece(2, 3, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece12 = new Piece(2, 4, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece13 = new Piece(2, 5, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece14 = new Piece(2, 6, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece whitePiece15 = new Piece(2, 7, Constants.PlayerColors.WHITE, whitePieceSet);
        Piece kingPiece = new KingPiece(7, 4, Constants.PlayerColors.WHITE, whitePieceSet);
        whitePieceSet.add(whitePiece1);
        whitePieceSet.add(whitePiece2);
        whitePieceSet.add(whitePiece3);
        whitePieceSet.add(whitePiece4);
        whitePieceSet.add(whitePiece5);
        whitePieceSet.add(whitePiece6);
        whitePieceSet.add(whitePiece7);
        whitePieceSet.add(whitePiece8);
        whitePieceSet.add(whitePiece9);
        whitePieceSet.add(whitePiece10);
        whitePieceSet.add(whitePiece11);
        whitePieceSet.add(whitePiece12);
        whitePieceSet.add(whitePiece13);
        whitePieceSet.add(whitePiece14);
        whitePieceSet.add(whitePiece15);
        whitePieceSet.add(kingPiece);
        
        Set<Piece> blackPieceSet = new HashSet<>();
        Piece blackPiece1 = new Piece(3, 1, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece2 = new Piece(4, 1, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece3 = new Piece(4, 2, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece4 = new Piece(4, 6, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece5 = new Piece(5, 2, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece6 = new Piece(5, 3, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece7 = new Piece(5, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece8 = new Piece(5, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece9 = new Piece(6, 1, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece10 = new Piece(6, 2, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece11 = new Piece(6, 4, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece12 = new Piece(6, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece13 = new Piece(6, 6, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece14 = new Piece(7, 5, Constants.PlayerColors.BLACK, blackPieceSet);
        Piece blackPiece15 = new Piece(7, 6, Constants.PlayerColors.BLACK, blackPieceSet);

        blackPieceSet.add(blackPiece1);
        blackPieceSet.add(blackPiece2);
        blackPieceSet.add(blackPiece3);
        blackPieceSet.add(blackPiece4);
        blackPieceSet.add(blackPiece5);
        blackPieceSet.add(blackPiece6);
        blackPieceSet.add(blackPiece7);
        blackPieceSet.add(blackPiece8);
        blackPieceSet.add(blackPiece9);
        blackPieceSet.add(blackPiece10);
        blackPieceSet.add(blackPiece11);
        blackPieceSet.add(blackPiece12);
        blackPieceSet.add(blackPiece13);
        blackPieceSet.add(blackPiece14);
        blackPieceSet.add(blackPiece15);
        
        Piece[][] board = new Piece[8][8];
        board[whitePiece1.getRow()][whitePiece1.getCol()] = whitePiece1;
        board[whitePiece2.getRow()][whitePiece2.getCol()] = whitePiece2;
        board[whitePiece3.getRow()][whitePiece3.getCol()] = whitePiece3;
        board[whitePiece4.getRow()][whitePiece4.getCol()] = whitePiece4;
        board[whitePiece5.getRow()][whitePiece5.getCol()] = whitePiece5;
        board[whitePiece6.getRow()][whitePiece6.getCol()] = whitePiece6;
        board[whitePiece7.getRow()][whitePiece7.getCol()] = whitePiece7;
        board[whitePiece8.getRow()][whitePiece8.getCol()] = whitePiece8;
        board[whitePiece9.getRow()][whitePiece9.getCol()] = whitePiece9;
        board[whitePiece10.getRow()][whitePiece10.getCol()] = whitePiece10;
        board[whitePiece11.getRow()][whitePiece11.getCol()] = whitePiece11;
        board[whitePiece12.getRow()][whitePiece12.getCol()] = whitePiece12;
        board[whitePiece13.getRow()][whitePiece13.getCol()] = whitePiece13;
        board[whitePiece14.getRow()][whitePiece14.getCol()] = whitePiece14;
        board[whitePiece15.getRow()][whitePiece15.getCol()] = whitePiece15;
        board[kingPiece.getRow()][kingPiece.getCol()] = kingPiece;
        
        board[blackPiece1.getRow()][blackPiece1.getCol()] = blackPiece1;
        board[blackPiece2.getRow()][blackPiece2.getCol()] = blackPiece2;
        board[blackPiece3.getRow()][blackPiece3.getCol()] = blackPiece3;
        board[blackPiece4.getRow()][blackPiece4.getCol()] = blackPiece4;
        board[blackPiece5.getRow()][blackPiece5.getCol()] = blackPiece5;
        board[blackPiece6.getRow()][blackPiece6.getCol()] = blackPiece6;
        board[blackPiece7.getRow()][blackPiece7.getCol()] = blackPiece7;
        board[blackPiece8.getRow()][blackPiece8.getCol()] = blackPiece8;
        board[blackPiece9.getRow()][blackPiece9.getCol()] = blackPiece9;
        board[blackPiece10.getRow()][blackPiece10.getCol()] = blackPiece10;
        board[blackPiece11.getRow()][blackPiece11.getCol()] = blackPiece11;
        board[blackPiece12.getRow()][blackPiece12.getCol()] = blackPiece12;
        board[blackPiece13.getRow()][blackPiece13.getCol()] = blackPiece13;
        board[blackPiece14.getRow()][blackPiece14.getCol()] = blackPiece14;
        board[blackPiece15.getRow()][blackPiece15.getCol()] = blackPiece15;
        
        State s = new State(whitePieceSet, blackPieceSet, board);
        Player negamax = Player.generatePlayer(Player.PlayerTypes.NEGAMAX.getValue(), Constants.PlayerColors.BLACK, blackPieceSet);
        Move m = negamax.selectMove(s);
        
//        DeepestMultiJumpFinder finder = new DeepestMultiJumpFinder();
//        Set<Move> moves = finder.findDeepestNode(s);
//        Move m = moves.iterator().next();
//        m.execute(s);
        System.out.println("debug");
    }
    
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
        createRegularVSIDNegamax();
    }
    
}
