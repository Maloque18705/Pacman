package entities;
import java.awt.*;

public abstract class Entity {
    protected int xPos;
    protected int yPos;
    protected int size;
    protected boolean isDestroyed = false;

    public Entity(int xPos, int yPos, int size) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
    }

    public void draw(Graphics2D g) {}

    public void update() {}


    public void setDestroyed() {
        this.xPos = -32;
        this.yPos = -32;
        isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
    public int getXPos() {
        return xPos;
    }
    
    public int getYPos() {
        return yPos;
    }

    public int getSize() {
        return size;
    }

    public abstract Rectangle getRect();
}
