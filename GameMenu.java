import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class GameMenu extends JFrame {
    public GameMenu() {
        super();
        setUndecorated(true); // e krijon cdo gje ne dritare vet, pa kte num shfaqet minimize, exit etj
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Curlz MT", Font.PLAIN, 50));
        titleLabel.setForeground(Color.BLUE);

        JButton exitSaveButton = new JButton("Exit and Save");
        JButton exitWithoutSaveButton = new JButton("Exit without Saving");

        Dimension buttonSize = new Dimension(300, 40);
        exitSaveButton.setPreferredSize(buttonSize);
        exitWithoutSaveButton.setPreferredSize(buttonSize);

        exitSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            MainFrame.kontrolliLojes.ruajLojen();
            dispose();
            System.exit(0);
            }
        });

        exitWithoutSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            dispose();
            System.exit(0);
            }
        });

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(10, 0, 0, 0); // Adjust top margin 

        buttonPanel.add(exitSaveButton, grid);

        grid.gridy = 1;
        grid.insets = new Insets(10, 0, 0, 0); // Adjust top margin

        buttonPanel.add(exitWithoutSaveButton, grid);

        titlePanel.add(buttonPanel, BorderLayout.CENTER);

        // Set dark blue border
        titlePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 143), 5));

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.CENTER);

        setSize(400, 200);
        setResizable(false);

        setLocationRelativeTo(null);
        setVisible(true);
           
    }
}
