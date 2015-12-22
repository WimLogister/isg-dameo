package dameo.gametree;

import dameo.util.Constants;
import dameo.gameboard.KingPiece;
import dameo.gameboard.Piece;
import dameo.util.DameoUtil;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
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
            /*
            Create and store a new entry that is returned by call to getCachedEntry
            so the (secondary) hash key does not have to be computed twice.
            */
            cachedEntry = new TableEntry(0, TableValueFlagTypes.EXACT, null,
                totalHashSize, hash);
            table.put(hash.get(0, secondaryHashSize), cachedEntry);
            return TableCheckResultTypes.EMPTY;
        }
        cachedEntry = table.get(hash.get(0, primaryHashSize-1));
        /* Case: identical entry (based on secondary hash code) exists in table */
        if (!cachedEntry.getHashkey().get(primaryHashSize, totalHashSize-1).equals(
                hash.get(primaryHashSize, totalHashSize))) {
            return TableCheckResultTypes.VALID;
        }
        /* Case: entry exists with identical primary hash code but different hash key.
        Since I use the "always take newest" replacement scheme, I return the old
        table entry but with the new hash key. The other values such as value, depth
        and flag will be adjusted in the alpha-beta code. */
        cachedEntry.setHashkey(hash);
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
     * Enumerated type for table value flags.
     */
    public enum TableValueFlagTypes {
        EXACT, LOWER, UPPER;
    }
    
    public enum TableCheckResultTypes {
        EMPTY, VALID, COLLISION;
    }
    
}
