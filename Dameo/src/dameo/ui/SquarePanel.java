package dameo.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Wim
 */
public class SquarePanel extends JPanel {
    Color color = Color.GREEN;
    static boolean isGreen = false;
    int x, y, width, height;

    public SquarePanel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if (isGreen) {
            color = Color.WHITE;
            isGreen = false;
        }
        else {
            color = Color.GREEN;
            isGreen = true;
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
