package dameo;

/**
 *
 * @author Wim
 */
public class Dameo {
    
    public static final DameoEngine ENGINE = DameoEngine.createEngine();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ENGINE.start();
    }
    
}
