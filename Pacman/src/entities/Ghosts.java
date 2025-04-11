package entities;
import java.util.HashSet;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.ProgressMonitor;
abstract class Ghosts {
   protected int x, y;
   protected int startX, startY;
   protected int width, height;
   protected Image image;
   protected char direction = 'U';
   protected int velocityX=0, velocityY=0;
   protected int tileSize = 32;

   public Ghosts(Image image, int x, int y, int width, int height) {
    this.image = image;
    this.x = x;
    this.y = y;
    this.startX = startX;
    this.startY = startY;
    this.width = width;
    this.height = height;
   }

    public abstract Point getTarget(Point pacmanPos, Point blinkyPos);

    public void updateDirection(char direction, HashSet<PacMan.Block> walls, int boardWidth) {
        char prevDirection = this.direction;
        this.direction = direction;
        updateVelocity();

        this.x += velocityX;
        this.y += velocityY;

        for(PacMan.Block wall: walls) {
            if (collision(this, wall) || x<=0 || x + width >= boardWidth) {
                this.x -= velocityX;
                this.y -= velocityY;
                this.direction = prevDirection;
                updateVelocity();
                break;
            }
        }
    }

    public void updateVelocity() {
        switch (this.direction) {
            case 'U':
                this.velocityX = 0;
                this.velocityY = -tileSize/4;
                break;
            
            case 'D':
                this.velocityX = 0;
                this.velocityY = tileSize/4;
                break;
            
            case 'L':
                this.velocityX = -tileSize/4;
                this.velocityY = 0;
                break;
            case 'R':
                this.velocityX = tileSize/4;
                this.velocityY = 0;
                break;
            default:
                break;
        }
    }

    public void reset() {
        this.x = this.startX;
        this.y = this.startY;
    }

    public boolean collision(Ghosts a, PacMan.Block b) {
        return a.x < b.x + b.width && a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    public void move() {
        this.x += this.velocityX;
        this.y += this.velocityY;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }    
}

class Blinky extends Ghosts {
    public Blinky(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    @Override
    public Point getTarget(Point pacmanPos, Point blinkyPos) {
        int dx = pacmanPos.x - blinkyPos.x;
        int dy = pacmanPos.y - blinkyPos.y;
        return new Point(pacmanPos.x + dx, pacmanPos.y + dy);
    }
}

class Pinky extends Ghosts {
    public Pinky(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    @Override
    public Point getTarget(Point pacmanPos, Point blinkyPos) {
        return new Point(pacmanPos.x + 2*tileSize, pacmanPos.y);
    }
}

class Inky extends Ghosts {
    public Inky(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    @Override
    public Point getTarget(Point pacmanPos, Point blinkyPos) {
        int dx = pacmanPos.x - blinkyPos.x;
        int dy = pacmanPos.y - blinkyPos.y;
        return new Point(pacmanPos.x + dx, pacmanPos.y + dy);
    }
}

class Clyde extends Ghosts {
    public Clyde(Image image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

    @Override
    public Point getTarget(Point pacmanPos, Point blinkyPos) {
        double distance = new Point(x, y).distance(pacmanPos);
        if (distance > 8*tileSize) {
            return pacmanPos;
        } else {
            return new Point(0, 31*tileSize);
        }
    }
}
