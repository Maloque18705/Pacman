package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;

public abstract class MovingEntity extends Entity{

    protected int xSpeed = 0;
    protected int ySpeed = 0;
    protected int direction = 0;
    protected int speed;
    
    protected float imgSpeed = 0.3f;
    protected int imagePerCycle;
    protected float subImg;
    protected BufferedImage sprite;
    protected int startXPos, startYPos;
    
    public MovingEntity(int xPos, int yPos, int size, int speed, String spriteName, int imagePerCycle, float imgSpeed) {
        super(xPos, yPos, size);
        this.speed = speed;
        this.imagePerCycle = imagePerCycle;
        this.imgSpeed = imgSpeed;
        this.startXPos = xPos;
        this.startYPos = yPos;
        try {
            String path = "./res/img/" + spriteName;
            this.sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        updatePos();
    }

    public void updatePos() {
        if(!(xPos == 0 && yPos == 0)) {
            xPos += xSpeed;
            yPos += ySpeed;

            if(xSpeed >0) direction = 0;
            else if (xSpeed<0) direction = 1;
            else if (ySpeed<0) direction = 2;
            else if (ySpeed>0) direction = 3;

            subImg += imgSpeed;
            if (subImg >= imagePerCycle) {
                subImg = 0;
            }
        }

        if(xPos > GamePanel.width) {
            xPos = -size + speed;
        }

        if (xPos < -size + speed) {
            xPos = GamePanel.width;
        }

        if (yPos < -size + speed) {
            yPos = GamePanel.height;
        }

        if (yPos > GamePanel.height) {
            yPos = -size + speed;
        }
    }


    @Override
    public void draw(Graphics2D g) {
        g.drawImage(sprite.getSubimage(direction * imagePerCycle * size + size * (int) subImg, 0, size, size), xPos, yPos, null);
    } 

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean onTheGrid() {
        return (xPos % 8 == 0 && yPos % 8 == 0);
    }

    public boolean onGameplayWindow() {
        return xPos > 0 && xPos < GamePanel.width && yPos > 0 && yPos < GamePanel.height;
    }

    public int getXSpeed() {
        return xSpeed;
    }
    
    public int getYSpeed() {
        return ySpeed;
    }
    
    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }
    
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void reset() {
        this.xPos = startXPos;
        this.yPos = startYPos;
        this.direction = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(xPos, yPos, size, size);
    }
}
