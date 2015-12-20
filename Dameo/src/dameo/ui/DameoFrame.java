package dameo.ui;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.KingPiece;
import dameo.Piece;
import dameo.gametree.State;
import dameo.move.Move;
import dameo.players.Player;
import dameo.players.Player.PlayerTypes;
import dameo.util.Observer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Displays the game board, accepts player move input.
 * Is notified by game engine of changes in board state.
 * @author Wim
 */
public class DameoFrame extends JFrame implements Observer {
    
    private JPanel boardPanel;
    private JPanel dashBoardPanel;
    private final DameoEngine engine;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;
    public PlayerTypes p1;
    public PlayerTypes p2;
    private Move selectedMove;

    public DameoFrame() throws HeadlessException {
        getPlayerSetupInput();
        engine = DameoEngine.createInputPlayerGame(p1, p2);
        createBoardPanel();
        createDashBoardPanel();
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
        this.selectedMove = null;
    }
    
    public void start() {
        DameoEngine.DEBUG = 1;
        engine.setObserver(this);
        engine.start();
    }
   
    
    private void getPlayerSetupInput() {
        Object[] possibilities = {"Human", "AI", "Random"};
        String s1 = (String)JOptionPane.showInputDialog(this, "Select a player type for white player",
                "Get player input", JOptionPane.PLAIN_MESSAGE,null, possibilities, "Human");
        if ((s1 != null) && (s1.length() > 0)) {
            if (s1.equals("Human")) {
                p1 = PlayerTypes.HUMAN;
            }
            else if (s1.equals("AI")) {
                p1 = PlayerTypes.NEGAMAX;
            }
            else if (s1.equals("Random")) {
                p1 = PlayerTypes.RANDOM;
            }
        }
        String s2 = (String)JOptionPane.showInputDialog(this, "Select a player type for black player",
                "Get player input", JOptionPane.PLAIN_MESSAGE,null, possibilities, "Human");
        if ((s2 != null) && (s2.length() > 0)) {
            if (s2.equals("Human")) {
                p2 = PlayerTypes.HUMAN;
            }
            else if (s2.equals("AI")) {
                p2 = PlayerTypes.NEGAMAX;
            }
            else if (s2.equals("Random")) {
                p2 = PlayerTypes.RANDOM;
            }
        }
    }
    
    
    private void createBoardPanel() {
        boardPanel = new DameoBoardPanel();
        this.getContentPane().add(boardPanel, BorderLayout.CENTER);
    }
    
    private void createDashBoardPanel() {
        dashBoardPanel = new JPanel();
        dashBoardPanel.setLayout(new BoxLayout(dashBoardPanel, BoxLayout.Y_AXIS));
        List<Player> list = engine.getPlayers();
        dashBoardPanel.add(new JLabel("White Player"));
        dashBoardPanel.add(new JLabel(String.format("Type: %s", list.get(0).getPlayerType())));
        dashBoardPanel.add(new JLabel("Black Player"));
        dashBoardPanel.add(new JLabel(String.format("Type: %s", list.get(1).getPlayerType())));
        dashBoardPanel.setPreferredSize(new Dimension(100, 200));
        this.getContentPane().add(dashBoardPanel, BorderLayout.EAST);
    }
    
    @Override
    public void update() {
        this.getContentPane().removeAll();
        createBoardPanel();
        createDashBoardPanel();
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
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
                        g.setColor(khaki);
                    }
                    else {
                        g.setColor(green);
                    }
                    g.fillRect(x, y, squareSide, squareSide);
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
                int py = 7-p.getRow();
                if (p.getColor() == Constants.PlayerColors.WHITE) {
                    g2d.setColor(white);
                }
                else {
                    g2d.setColor(black);
                }
                Ellipse2D.Double circle = new Ellipse2D.Double(px*squareSide,
                        py*squareSide, squareSide, squareSide);
                g2d.fill(circle);
                g2d.setColor(Color.BLACK);
                if (p instanceof KingPiece) {
                    Ellipse2D.Double kingCircle = new Ellipse2D.Double(px*squareSide+5,
                            py*squareSide, squareSide-10, squareSide-10);
                    g2d.draw(kingCircle);
                }
                g2d.drawString(String.format("%d,%d", px+1, ((-py+7)%8)+1), px*squareSide+25, py*squareSide+35);
            }
        }
    }
    
}
