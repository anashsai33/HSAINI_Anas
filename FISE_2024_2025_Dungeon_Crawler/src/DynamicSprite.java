import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;

    private int health = 100;     // Vie actuelle
    private int maxHealth = 100;  // Vie maximale

    private long lastCollisionTime = 0;  // Temps de la dernière collision en millisecondes
    public static final int INVINCIBILITY_DURATION = 2000;  // 2 secondes d'invincibilité
    private boolean isVisible = true;  // Par défaut, le personnage est visible

    private double normalSpeed = 5;  // Vitesse normale
    private double runningSpeed = 10;  // Vitesse en course

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) health = 0;  // Ne pas descendre en-dessous de 0
    }

    public int getHealth() {
        return health;
    }

    public void drawHealthBar(Graphics g) {
        int barWidth = (int) width; // Largeur de la barre = largeur du sprite
        int barHeight = 5;          // Hauteur de la barre

        // Position de la barre au-dessus du sprite
        int barX = (int) x;
        int barY = (int) y - barHeight - 5;

        // Calcul de la largeur en fonction de la vie restante
        int healthWidth = (int) ((health / (double) maxHealth) * barWidth);

        // Dessiner la barre : fond rouge + vie verte
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight); // Fond rouge

        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, healthWidth, barHeight); // Vie restante (vert)

        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight); // Contour noir
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    public long getLastCollisionTime() {
        return lastCollisionTime;
    }

    public void setLastCollisionTime(long time) {
        this.lastCollisionTime = time;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void enableRunning(boolean isRunning) {
        speed = isRunning ? runningSpeed : normalSpeed;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }


    @Override
    public void draw(Graphics g) {

        if (!isVisible) {
            return;  // Ne dessine pas le sprite s'il est invisible
        }

        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);
        drawHealthBar(g);
    }
}
