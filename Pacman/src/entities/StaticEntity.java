package entities;
import java.awt.*;

public abstract class StaticEntity extends Entity {
    protected Rectangle rect;
    public StaticEntity(int xPos, int yPos, int size) {
        super(xPos, yPos, size);
        this.rect = new Rectangle(xPos, yPos, size, size);
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }
}
