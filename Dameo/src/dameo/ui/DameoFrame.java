package dameo.ui;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.Piece;
import dameo.gametree.State;
import dameo.util.Observer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Wim
 */
public class DameoFrame extends JFrame implements Observer {
    
    private JPanel boardPanel;
    private JPanel controlPanel;
    private JPanel inputPanel;
    private final DameoEngine engine;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    public DameoFrame() throws HeadlessException {
        DameoEngine.DEBUG = 1;
        engine = DameoEngine.createRandomPlayerGame();
        engine.setObserver(this);
        createBoardPanel();
        engine.start();
    }
    
    private void createBoardPanel() {
        boardPanel = new DameoBoardPanel();
        this.getContentPane().add(boardPanel);
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
    }
    
    private void createControlPanel() {
        
    }
    
    private void createInputPanel() {
        
    }

    @Override
    public void update() {
        this.getContentPane().removeAll();
        createBoardPanel();
        
    }
    
    class DameoBoardPanel extends JPanel {
        
        @Override
        public void paintComponent(Graphics g) {
            final int numRows = 8;
            final int numCols = 8;
            final int squareSide = 65;
            int x, y;
            Color green = new Color(0,139,0);
            Color khaki = new Color(255,246,143);
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    x = col * squareSide;
                    y = row * squareSide;
                    if (row % 2 == col % 2){
                        g.setColor(green);
                    }
                    else {
                        g.setColor(khaki);
                    }
                    g.fillRect(x, y, squareSide, squareSide);
//                    this.add(new SquarePanel(col*this.getWidth()/cols,
//                            row*this.getHeight()/rows, this.getWidth()/cols,
//                            this.getHeight()/rows));
                }
            }
            State s = engine.getCurrentState();
            Set<Piece> allPieces = new HashSet<>(s.getCurrentPlayerPieces());
            allPieces.addAll(s.getOpponentPieces());
            Color black = Color.RED;
            Color white = Color.WHITE;
            Graphics2D g2d = (Graphics2D) g;
            for (Piece p : allPieces) {
                int px = p.getCol();
                int py = p.getRow();
                if (p.getColor() == Constants.PlayerColors.WHITE) {
                    g2d.setColor(white);
                }
                else {
                    g2d.setColor(black);
                }
                Ellipse2D.Double circle = new Ellipse2D.Double(px*squareSide,
                        py*squareSide, squareSide, squareSide);
                g2d.fill(circle);
            }
        }
    }
    
    public static void main(String[] args) {
        DameoFrame frame = new DameoFrame();
        frame.setSize(WIDTH, HEIGHT);
//        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
