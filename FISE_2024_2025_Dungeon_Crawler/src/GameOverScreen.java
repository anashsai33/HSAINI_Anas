import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameOverScreen {
    private JFrame parentFrame; // Référence à la fenêtre principale

    public GameOverScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;  // Récupère la fenêtre principale pour la redémarrer

        // Crée une nouvelle fenêtre pour l'écran Game Over
        JFrame frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Ajouter un message Game Over
        JLabel messageLabel = new JLabel("Game Over!", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(messageLabel, BorderLayout.CENTER);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Bouton Recommencer
        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Ferme la fenêtre Game Over
                parentFrame.dispose();  // Ferme la fenêtre principale
                try {
                    new Main();  // Redémarre le jeu
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Bouton Quitter
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Quitte le jeu
            }
        });

        // Ajouter les boutons au panneau
        buttonPanel.add(restartButton);
        buttonPanel.add(quitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Affiche la fenêtre Game Over
        frame.setVisible(true);
    }
}