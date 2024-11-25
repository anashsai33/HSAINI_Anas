import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen {
    public static void main(String[] args) {
        // Crée une fenêtre pour l'écran titre
        JFrame frame = new JFrame("Écran Titre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Taille de l'écran titre
        frame.setLayout(new BorderLayout());

        // Titre du jeu
        JLabel titleLabel = new JLabel("Bienvenue dans le Dongeon Crawler!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.CENTER);

        // Bouton pour démarrer le jeu
        JButton startButton = new JButton("Commencer le jeu");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(startButton, BorderLayout.SOUTH);

        // Action pour lancer le jeu
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Ferme l'écran titre
                try {
                    new Main(); // Lance la classe Main
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true); // Affiche l'écran titre
    }

}
