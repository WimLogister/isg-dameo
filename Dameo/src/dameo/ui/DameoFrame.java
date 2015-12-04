package dameo.ui;

import dameo.Constants;
import dameo.DameoEngine;
import dameo.Piece;
import dameo.gametree.State;
import dameo.players.Player;
import dameo.players.Player.PlayerTypes;
import dameo.util.Observer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.control.RadioButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Wim
 */
public class DameoFrame extends JFrame implements Observer {
    
    private JPanel boardPanel;
    private JPanel dashBoardPanel;
    private JPanel controlPanel;
    private JPanel inputPanel;
    private final DameoEngine engine;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;
    public PlayerTypes p1;
    public PlayerTypes p2;

    public DameoFrame() throws HeadlessException {
        engine = DameoEngine.createRandomPlayerGame();
        createBoardPanel();
        createDashBoardPanel();
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
    }
    
    public void start() {
        DameoEngine.DEBUG = 1;
        engine.setObserver(this);
        engine.start();
        
    }
    
    private void runGameSetupFrame() {
        JFrame setupFrame = new JFrame();
        setupFrame.setLayout(new BoxLayout(setupFrame, BoxLayout.Y_AXIS));
        setupFrame.add(new JLabel("White player type:"));
        
        ButtonGroup p1Group = new ButtonGroup();
        
        JRadioButton humanButton1 = new JRadioButton("Human"); p1Group.add(humanButton1);
        humanButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1 = PlayerTypes.HUMAN;
            }
        });
        JRadioButton aiButton1 = new JRadioButton("AI"); p1Group.add(aiButton1);
        aiButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1 = PlayerTypes.NEGAMAX;
            }
        });
        JRadioButton randomButton1 = new JRadioButton("Random"); p1Group.add(randomButton1);
        randomButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1 = PlayerTypes.RANDOM;
            }
        });
        
        ButtonGroup p2Group = new ButtonGroup();
        
        JRadioButton humanButton2 = new JRadioButton("Human"); p2Group.add(humanButton2);
        humanButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2 = PlayerTypes.HUMAN;
            }
        });
        JRadioButton aiButton2 = new JRadioButton("AI"); p2Group.add(aiButton2);
        aiButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2 = PlayerTypes.NEGAMAX;
            }
        });
        JRadioButton randomButton2 = new JRadioButton("Random"); p2Group.add(randomButton2);
        randomButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2 = PlayerTypes.RANDOM;
            }
        });
        
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
    
    private void createControlPanel() {
        
    }
    
    private void createInputPanel() {
        
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
                        g.setColor(green);
                    }
                    else {
                        g.setColor(khaki);
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
        frame.start();
    }
    
}
