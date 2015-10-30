package dameo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Wim
 */
public class Util {
    
    private static final BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));
    
    public static String getConsoleInput() {
        String s = "";
        try {
            s = br.readLine();
        } catch (IOException e) {
        }
        return s;
    }
    
}
