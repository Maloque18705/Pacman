package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Pacman {

    private int x, y;
    private int velocityX, velocityY;
    private int size;
    private char direction; // 'U', 'D', 'L', 'R'
    private int speed = 4;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanRightImage;
    private Image pacmanLeftImage;

    Block pacman;

    public Pacman(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.size = size;
        // pacmanImage
        this.direction = 'R';
    }

    public void move(char[][] map) {
        int nextX = x + velocityX;
        int nextY = y + velocityY;

        if (!isWallCollision(nextX, nextY, map)) {
            
        }
    }

    private boolean isWallCollision(int nextX, int nextY, char[][] map) {

    }

    public void updateDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                direction = 'U';
                velocityX = 0;
                velocityY = -speed; 
                break;
            
            case KeyEvent.VK_DOWN:
                direction = 'D';
                velocityX = 0;
                velocityY = speed;
                break;

            case KeyEvent.VK_LEFT:
                direction = 'L';
                velocityX = -speed;
                velocityY = 0;
                break;
            case KeyEvent.VK_RIGHT:
                direction = 'R';
                velocityX = speed;
                velocityY = 0;
                break;
        }
    }

    public void draw(Graphics g) {
        g.drawImage();
    }

}
