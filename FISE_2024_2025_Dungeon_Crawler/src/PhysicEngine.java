import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList<Sprite> environment;
    private ArrayList<SolidSprite> enemies;

    public PhysicEngine() {  // Constructeur
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public void addToEnvironmentList(Sprite sprite) {
        if (!environment.contains(sprite)) {
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    public void addToMovingSpriteList(DynamicSprite sprite) {
        if (!movingSpriteList.contains(sprite)) {
            movingSpriteList.add(sprite);
        }
    }

    public void addEnemy(SolidSprite enemy) {
        if (!enemies.contains(enemy)) {
            enemies.add(enemy);
        }
    }

    @Override
    public void update() {
        System.out.println("PhysicEngine: update() called");
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            // Le héros se déplace
            dynamicSprite.moveIfPossible(environment);

            // Vérification des collisions avec les ennemis (pièges)
            for (SolidSprite enemy : enemies) {
                if (dynamicSprite.getHitBox().intersects(enemy.getHitBox())) {
                    long currentTime = System.currentTimeMillis();

                    // Si le héros n'est pas en période d'invincibilité
                    if (currentTime - dynamicSprite.getLastCollisionTime() > DynamicSprite.INVINCIBILITY_DURATION) {
                        // Réduction de la vie
                        dynamicSprite.decreaseHealth(25);  // Réduit de 10 points
                        dynamicSprite.setLastCollisionTime(currentTime);  // Met à jour le temps de collision
                        System.out.println("Collision avec un piège ! Vie restante : " + dynamicSprite.getHealth());

                        // Si la vie atteint 0, déclencher le Game Over
                        if (dynamicSprite.getHealth() <= 0) {
                            System.out.println("Game Over déclenché !");
                            showGameOverScreen();  // Affiche l'écran Game Over
                            return;  // Arrête l'exécution
                        }
                    } else {
                        System.out.println("Collision ignorée (invincibilité active) !");
                    }
                }
            }
        }
    }

    private void showGameOverScreen() {
        // Créer une nouvelle fenêtre pour Game Over
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(400, 300);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setLayout(new BorderLayout());

        // Ajouter un message "Game Over"
        JLabel messageLabel = new JLabel("Game Over!", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameOverFrame.add(messageLabel, BorderLayout.CENTER);

        // Ajouter des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton restartButton = new JButton("Recommencer");
        restartButton.addActionListener(e -> {
            gameOverFrame.dispose();  // Fermer l'écran Game Over
            try {
                new Main();  // Relancer le jeu
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));  // Quitter l'application

        buttonPanel.add(restartButton);
        buttonPanel.add(quitButton);

        gameOverFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Afficher la fenêtre Game Over
        gameOverFrame.setVisible(true);
    }
}
