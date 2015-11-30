package dameo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author Wim
 */
public class DameoUtil {
    
    public static final Random rng = new Random(Constants.RANDOM_SEED);
    private static final BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Returns a randomly selected number in range [from,to].
     * @param from
     * @param to
     * @return 
     */
    public static int getRandomIntFromTo(int from, int to) {
        return from + rng.nextInt((to+1)-from);
    }
    
    /**
     * Convenience method for reading console input
     * @return 
     */
    public static String getConsoleInput() {
        String s = "";
        try {
            s = br.readLine();
        } catch (IOException e) {
        }
        return s;
    }
    
    public static double mean(double[] values) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum/values.length;
    }
    
}
