package dameo.ui;

import dameo.DameoEngine;
import dameo.util.Observer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Wim
 */
public class DameoFrame extends JFrame {
    
    private JPanel boardPanel;
    private JPanel controlPanel;
    private JPanel inputPanel;
    private final DameoEngine engine;

    public DameoFrame() throws HeadlessException {
        engine = DameoEngine.createEngine();
    }
    
    private void createBoardPanel() {
        
    }
    
    private void createControlPanel() {
        
    }
    
    private void createInputPanel() {
        
    }
    
    class DameoBoardPanel extends JPanel implements Observer {

        @Override
        public void paintComponent(Graphics g) {
            
        }
        @Override
        public void update() {
            engine.getCurrentState();
        }
        
    }
    
}
