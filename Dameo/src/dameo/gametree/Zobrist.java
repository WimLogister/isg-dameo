package dameo.gametree;

import dameo.Piece;
import dameo.util.DameoUtil;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Wim
 */
public class Zobrist {
    
    /* Table for storing hash values. Each row represents one square on the board
    and each column one possible that can be in the square.*/
    private final BitSet[][] table = new BitSet[64][4];
    private final int primaryHashSize;
    private final int secondaryHashSize;
    private final int totalHashSize;

    public Zobrist(int primaryHashSize, int secHashSize) {
        this.primaryHashSize = primaryHashSize;
        this.secondaryHashSize = secHashSize;
        this.totalHashSize = primaryHashSize + secondaryHashSize;
    }
    
    public void createTable() {
        assert ((primaryHashSize + secondaryHashSize) % 8 == 0) : "Primary and secondary hash key size should be divisible by 8.";
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = Zobrist.generateRandomBitSet(primaryHashSize+secondaryHashSize);
            }
        }
    }
    
    /**
     * Return Zobrist hash for parameter State.
     * @param s
     * @return 
     */
    public BitSet hash(State s) {
        BitSet hash = new BitSet(totalHashSize);
        
        /* Go through all current player's pieces */
        for (Piece p : s.getCurrentPlayerPieces()) {
            int tableRowIndex = (p.getCol() * 8 + p.getRow());
            int tableColIndex = p.getZobristValue();
            hash.xor(table[tableRowIndex][tableColIndex]);
        }
        /* Go through all opponent's pieces */
        for (Piece p : s.getOpponentPieces()) {
            int tableRowIndex = (p.getCol() * 8 + p.getRow());
            int tableColIndex = p.getZobristValue();
            hash.xor(table[tableRowIndex][tableColIndex]);
        }
        return hash;
    }
    
    public static BitSet generateRandomBitSet(int numOfBytes) {
        byte[] randomBytes = new byte[numOfBytes];
        DameoUtil.SECURE_RANDOM.nextBytes(randomBytes);
        return BitSet.valueOf(randomBytes);
    }
    
    public static void main(String[] args) {
        System.out.println(42 ^ 15);
        SecureRandom rand = new SecureRandom();
        final int NUM_BYTES = 8;
        byte[] randomBytes = new byte[NUM_BYTES];
        rand.nextBytes(randomBytes);
        BitSet set1 = BitSet.valueOf(randomBytes);
        BitSet set2 = BitSet.valueOf(randomBytes);
        System.out.println(set1.toString());
    }
    
}
