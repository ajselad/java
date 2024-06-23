import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menuja extends JFrame {
    private JLabel titulliLojes;
    private JButton loadGame;
    private JButton newGame;

    public Menuja() {
        super("Main Menu");
        setLayout(new BorderLayout());

        ImageIcon santaMazeImageIcon = new ImageIcon("C:\\Users\\Perdorues\\Pictures\\Title Sprite.png");

        Image santaMazeImage = santaMazeImageIcon.getImage().getScaledInstance(400, 122, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(santaMazeImage);

        titulliLojes = new JLabel(scaledImageIcon);
        add(titulliLojes, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        loadGame = new JButton("Load Game");
        loadGame.setPreferredSize(new Dimension(150, 40));
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainFrame(KonstanteLoje.SAVED_GAME);
                setVisible(false);
            }
        });
        buttonPanel.add(loadGame);

        newGame = new JButton("New Game");
        newGame.setPreferredSize(new Dimension(150, 40));
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame(KonstanteLoje.NEW_GAME);
                setVisible(false);
            }
        });

        buttonPanel.add(newGame);

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setPreferredSize(new Dimension(400, 225));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }
}
