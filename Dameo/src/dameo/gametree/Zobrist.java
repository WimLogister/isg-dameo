package dameo.gametree;

import java.security.SecureRandom;
import java.util.BitSet;

/**
 *
 * @author Wim
 */
public class Zobrist {
    private static final int WHITE_MAN = 1;
    private static final int BLACK_MAN = 2;
    private static final int WHITE_KING = 3;
    private static final int BLACK_KING = 4;
    
    /* Table for storing hash values. Each row represents one square on the board
    and each column one possible that can be in the square.*/
    private final int[][] table = new int[64][4];
    
    public void createTable() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println(42 ^ 15);
        SecureRandom rand = new SecureRandom();
        final int NUM_BYTES = 8;
        byte[] randomBytes = new byte[NUM_BYTES];
        rand.nextBytes(randomBytes);
        BitSet set = BitSet.valueOf(randomBytes);
        System.out.println(set.toString());
    }
    
}
