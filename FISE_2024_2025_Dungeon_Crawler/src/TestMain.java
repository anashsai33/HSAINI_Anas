import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class TestMain {

    public static void main(String[] args) throws Exception {
        // Crée la fenêtre principale
        JFrame frame = new JFrame("Dungeon Crawler");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Charge le héros
        DynamicSprite hero = new DynamicSprite(64, 64,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Charge la carte depuis le fichier level1.txt
        Playground level = new Playground("./data/level1.txt");

        // Récupère les éléments pour affichage et collision
        var sprites = level.getSpriteList();
        var solidSprites = level.getSolidSpriteList();

        // Panel pour afficher le jeu
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dessine tous les éléments de la carte
                for (Displayable sprite : sprites) {
                    sprite.draw(g);
                }

                // Dessine le héros
                hero.draw(g);

                // Affiche les coordonnées et la vie
                g.setColor(Color.WHITE);
                g.drawString("Héros: x=" + hero.getX() + ", y=" + hero.getY(), 10, 10);
                g.drawString("Vie: " + hero.getHealth(), 10, 25);
            }
        };
        panel.setBackground(Color.BLACK);

        // Ajoute le panel à la fenêtre
        frame.add(panel);

        // Écoute des touches clavier pour déplacer le héros
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP -> hero.setDirection(Direction.NORTH);
                    case java.awt.event.KeyEvent.VK_DOWN -> hero.setDirection(Direction.SOUTH);
                    case java.awt.event.KeyEvent.VK_LEFT -> hero.setDirection(Direction.WEST);
                    case java.awt.event.KeyEvent.VK_RIGHT -> hero.setDirection(Direction.EAST);
                }
                hero.moveIfPossible(solidSprites); // Vérifie les collisions avec l'environnement
                checkCollisions(hero, solidSprites); // Vérifie les collisions avec les pièges
                panel.repaint(); // Redessine l'écran
            }
        });

        frame.setVisible(true);
    }

    // Vérifie les collisions entre le héros et les pièges
    private static void checkCollisions(DynamicSprite hero, ArrayList<Sprite> solidSprites) {
        for (Sprite sprite : solidSprites) {
            if (sprite instanceof SolidSprite && hero.getHitBox().intersects(((SolidSprite) sprite).getHitBox())) {
                System.out.println("Collision avec un piège détectée !");
                hero.decreaseHealth(10); // Réduit la vie du héros
                if (hero.getHealth() <= 0) {
                    System.out.println("Game Over !");
                    showGameOverDialog();
                    return;
                }
            }
        }
    }

    // Affiche une boîte de dialogue Game Over
    private static void showGameOverDialog() {
        JOptionPane.showMessageDialog(null, "Game Over! Merci d'avoir joué.");
        System.exit(0);
    }
}