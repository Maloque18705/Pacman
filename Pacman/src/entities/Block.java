package entities;

public class Block {
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;
        
        int startX;
        int startY;

        char direction = 'U';
        int velocityX;
        int velocityy;
        
        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = X;
            this.startY = Y;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();

            this.x += velocityX;
            this.y += velocityY;

            for(Block wall: walls) {
                if (collision(this.wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -tileSize/4;
            }
            else if(this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = tileSize/4;    
            }
            else if(this.direction == 'L') {
                this.velocityX = -tileSize/4;
                this.velocityY = 0;
            }

            else if(this.direction == 'R') {
                this.velocityX = tileSize/4;
                this.velocityY = 0;
            }
        }

        void reset() {
            this.x = this.startX;
            this.y = this.startY;
        }
    }
}
