package dameo.gameboard;

import dameo.ui.DameoFrame;
import static dameo.ui.DameoFrame.HEIGHT;
import static dameo.ui.DameoFrame.WIDTH;
import javax.swing.JFrame;

/**
 * Main file of the project.
 * @author Wim
 */
public class Dameo {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DameoFrame frame = new DameoFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.start();
    }
    
}
