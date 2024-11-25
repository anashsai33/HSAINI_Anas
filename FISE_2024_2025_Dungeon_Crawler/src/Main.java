import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;
    
    private long startTime;  // Temps de début
    private Timer gameTimer; // Timer du jeu

    public Main() throws Exception{
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1000,1400);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(64,64,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50);

        // Ajouter les pièges manuellement
        SolidSprite trap1 = new SolidSprite(384, 64,
                ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap2 = new SolidSprite(1152, 128,
               ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap3 = new SolidSprite(704, 256,
                ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap4 = new SolidSprite(576, 320,
               ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap5 = new SolidSprite(960, 448,
                ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap6 = new SolidSprite(640, 640,
               ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap7 = new SolidSprite(1280, 704,
                ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap8 = new SolidSprite(192, 768,
                ImageIO.read(new File("./img/trap.png")), 64, 64);
        SolidSprite trap9 = new SolidSprite(1152, 768,
                ImageIO.read(new File("./img/trap.png")), 64, 64);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        Playground level = new Playground("./data/level1.txt");
        //SolidSprite testSprite = new DynamicSprite(100,100,test,0,0);
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());



        // Ajouter les pièges au moteur graphique et au moteur physique
        renderEngine.addToRenderList(trap1);
        renderEngine.addToRenderList(trap2);
        renderEngine.addToRenderList(trap3);
        renderEngine.addToRenderList(trap4);
        renderEngine.addToRenderList(trap5);
        renderEngine.addToRenderList(trap6);
        renderEngine.addToRenderList(trap7);
        renderEngine.addToRenderList(trap8);
        renderEngine.addToRenderList(trap9);

        physicEngine.addEnemy(trap1);
        physicEngine.addEnemy(trap2);
        physicEngine.addEnemy(trap3);
        physicEngine.addEnemy(trap4);
        physicEngine.addEnemy(trap5);
        physicEngine.addEnemy(trap6);
        physicEngine.addEnemy(trap7);
        physicEngine.addEnemy(trap8);
        physicEngine.addEnemy(trap9);

        displayZoneFrame.addKeyListener(gameEngine);
    }



    public void showGameOverPanel() {
        int choice = JOptionPane.showOptionDialog(
                displayZoneFrame,
                "Game Over! Voulez-vous recommencer ou quitter ?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Recommencer", "Quitter"},
                "Quitter"
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                new Main();  // Redémarre le jeu
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.exit(0);  // Quitte le jeu
        }
    }

    public JFrame getDisplayZoneFrame() {
        return displayZoneFrame;
    }

    public static void main (String[] args) throws Exception {
	// write your code here
        Main main = new Main();
    }
}
