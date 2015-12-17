package dameo.gametree;

import dameo.Constants;
import dameo.KingPiece;
import dameo.Piece;
import dameo.move.Move;
import dameo.util.DameoUtil;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A transposition table implementation for the Dameo board game.
 * @author Wim
 */
public class TranspositionTable {
    
    /* Table for storing bit strings used to represent different components of a
    given game state. Each row represents one square on the board and each column
    the different states that square can be in, i.e. the different pieces that can
    occupy it.*/
    private final BitSet[][] randomBitStrings = new BitSet[64][4];
    private final Map<BitSet, TableEntry> table = new HashMap<>((int)Math.pow(2, 20));
    private final int primaryHashSize;
    private final int secondaryHashSize;
    private final int totalHashSize;
    private TableEntry cachedEntry; /* Cache this entry for easy access by negamax */

    public TranspositionTable(int primaryHashSize, int secHashSize) {
        this.primaryHashSize = primaryHashSize;
        this.secondaryHashSize = secHashSize;
        this.totalHashSize = primaryHashSize + secondaryHashSize;
        createTable();
    }
    
    private void createTable() {
        assert ((primaryHashSize + secondaryHashSize) % 8 == 0) : "Primary and secondary hash key size should be divisible by 8.";
        for (int i = 0; i < randomBitStrings.length; i++) {
            for (int j = 0; j < randomBitStrings[0].length; j++) {
                randomBitStrings[i][j] = TranspositionTable.generateRandomBitSet(primaryHashSize+secondaryHashSize);
            }
        }
    }
    
    /**
     *
     * @param s
     * @return
     */
    public TableCheckResultTypes checkTable(State s) {
        BitSet hash = hash(s);
        /* Case: this entry does not exist in table yet */
        if (!table.containsKey(hash.get(0, primaryHashSize-1))) {
            return TableCheckResultTypes.EMPTY;
        }
        cachedEntry = table.get(hash.get(0, primaryHashSize-1));
        /* Case: identical entry (based on hash key) exists in table */
        if (cachedEntry.getHashkey().get(primaryHashSize, totalHashSize-1).equals(
                hash.get(primaryHashSize, totalHashSize))) {
            return TableCheckResultTypes.VALID;
        }
        /* Case: entry exists with identical primary hash code but different hash key */
        return TableCheckResultTypes.COLLISION;
    }
    
    /**
     * Return Zobrist hash for parameter State.
     * @param s
     * @return 
     */
    private BitSet hash(State s) {
        BitSet hash = new BitSet(totalHashSize);
        
        /* Go through all current player's pieces */
        for (Piece p : s.getCurrentPlayerPieces()) {
            int tableRowIndex = (p.getCol() * 8 + p.getRow());
            int tableColIndex = p.getZobristValue()-1;
            hash.xor(randomBitStrings[tableRowIndex][tableColIndex]);
        }
        /* Go through all opponent's pieces */
        for (Piece p : s.getOpponentPieces()) {
            int tableRowIndex = (p.getRow() * 8 + p.getCol());
            int tableColIndex = p.getZobristValue()-1;
            hash.xor(randomBitStrings[tableRowIndex][tableColIndex]);
        }
        return hash;
    }
    
    public static BitSet generateRandomBitSet(int numOfBytes) {
        byte[] randomBytes = new byte[numOfBytes];
        DameoUtil.SECURE_RANDOM.nextBytes(randomBytes);
        return BitSet.valueOf(randomBytes);
    }

    public TableEntry getCachedEntry() {
        return cachedEntry;
    }
    
    /**
     * Did some basic test cases on entire and primary hash key. Seems to be working.
     */
    public static void testHashFunction() {
        Set<Piece> whitePieces = new HashSet<>();
        Piece wp1 = new Piece(5, 5, Constants.PlayerColors.WHITE, whitePieces);
        Piece wp2 = new Piece(7, 7, Constants.PlayerColors.WHITE, whitePieces);
        whitePieces.add(wp1); whitePieces.add(wp2);
        
        Set<Piece> blackPieces = new HashSet<>();
        Piece bp1 = new KingPiece(2, 3, Constants.PlayerColors.BLACK, blackPieces);
        blackPieces.add(bp1);
        
        Piece[][] board = new Piece[8][8];
        board[wp1.getRow()][wp1.getCol()] = wp1;
        board[wp2.getRow()][wp2.getCol()] = wp2;
        board[bp1.getRow()][bp1.getCol()] = wp1;
        
        State s1 = new State(whitePieces, blackPieces, board);
        State s2 = new State(s1);
        s2.switchPlayers();
//        Piece extraPiece = new Piece(4, 4, Constants.PlayerColors.WHITE, s2.getCurrentPlayerPieces());
//        s2.getCurrentPlayerPieces().add(extraPiece);
//        s2.getBoard()[extraPiece.getRow()][extraPiece.getCol()] = extraPiece;
        
        TranspositionTable hasher = new TranspositionTable(20, 44);
        BitSet hash1 = hasher.hash(s1);
        BitSet hash2 = hasher.hash(s2);
        
        final int from = 0; final int to = 19;
        BitSet primaryHash1 = hash1.get(from, to);
        BitSet primaryHash2 = hash2.get(from, to);
        System.out.printf("States have equal hash codes: %b\n",primaryHash1.equals(primaryHash2));
    }
    
    public static void main(String[] args) {
        testHashFunction();
    }
    
    /**
     * Enumerated type for table value flags.
     */
    public enum TableValueFlagTypes {
        EXACT, LOWER, UPPER;
    }
    
    public enum TableCheckResultTypes {
        EMPTY, VALID, COLLISION;
    }
    
}
