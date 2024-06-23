import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    public static KontrolliLojes kontrolliLojes;
    private final JLabel thesareLabel;
    private final JLabel piketLabel;
    private final JLabel koordinatatLabel;

    public MainFrame(int game) {
        this.setTitle("SantaMaze");
        kontrolliLojes = new KontrolliLojes(this);
        if (game == KonstanteLoje.NEW_GAME)
            kontrolliLojes.filloLojen();
        else if (game == KonstanteLoje.SAVED_GAME)
            kontrolliLojes.filloLojen("save.dat");
        ImageIcon icon = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\pema.png");
        this.setIconImage(icon.getImage());
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        Color backgroundColor = new Color(0xb3ccda);
        getContentPane().setBackground(backgroundColor);

        JPanel mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int screenWidth = getWidth();
                int screenHeight = getHeight();
                int cellSize = Math.min(screenWidth / KonstanteLoje.KOLONA, screenHeight / KonstanteLoje.RRESHTA);

                int xOffset = (screenWidth - (KonstanteLoje.KOLONA * cellSize)) / 2;
                int yOffset = (screenHeight - (KonstanteLoje.RRESHTA * cellSize)) / 2;

                Labirint.cellSize = cellSize;

                g.setColor(backgroundColor);
                g.fillRect(0, 0, getWidth(), getHeight());

                kontrolliLojes.labirint.printoLabirintin(g, xOffset, yOffset);
            }

        };
        add(mazePanel, BorderLayout.CENTER);

        mazePanel.setFocusable(true);
        mazePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });

        thesareLabel = new JLabel("Dhurata: " + kontrolliLojes.lojtar.thesareTeMbledhura);
        piketLabel = new JLabel("Piket: " + kontrolliLojes.lojtar.piket);
        koordinatatLabel = new JLabel("Koordinatat+: (" + kontrolliLojes.lojtar.pozicioniRresht + ", " + kontrolliLojes.lojtar.pozicioniKolone + ")");
        JButton menu = new JButton("Exit Game");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameMenu gameMenu = new GameMenu();
                gameMenu.setVisible(true);
            }
        });

        JPanel labelPanel = new JPanel(new GridLayout(1, 3));
        labelPanel.add(thesareLabel);
        labelPanel.add(piketLabel);
        labelPanel.add(koordinatatLabel);
        labelPanel.add(menu);

        add(labelPanel, BorderLayout.NORTH);

        setVisible(true);
        mazePanel.requestFocusInWindow();
    }

    public void updateLabels() {
        thesareLabel.setText("Dhurata: " + kontrolliLojes.lojtar.thesareTeMbledhura);
        piketLabel.setText("Piket: " + kontrolliLojes.lojtar.piket);
        koordinatatLabel.setText("Koordinatat: (" + kontrolliLojes.lojtar.pozicioniRresht + ", " + kontrolliLojes.lojtar.pozicioniKolone + ")");
    }

    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                kontrolliLojes.luajRradhen("lart");
                break;
            case KeyEvent.VK_DOWN:
                kontrolliLojes.luajRradhen("poshte");
                break;
            case KeyEvent.VK_LEFT:
                kontrolliLojes.luajRradhen("majtas");
                break;
            case KeyEvent.VK_RIGHT:
                kontrolliLojes.luajRradhen("djathtas");
                break;
        }
        updateLabels();
        repaint();
    }
}
