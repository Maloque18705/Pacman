package inputs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            loadMap();
            resetPosition();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
             pacman.updateDirection('U');
        }
        else if(e.getKeyCode() == KeyEvent.VK_S) {
            pacman.updateDirection('D');
        }
        else if(e.getKeyCode() == KeyEvent.VK_A) {
            pacman.updateDirection('L');
        }
        else if(e.getKeyCode() == KeyEvent.VK_D) {
            pacman.updateDirection('R');
        }

        if (pacman.direction == 'U') {
            pacman.image = pacmanUpImage;
        }
        else if (pacman.direction == 'D') {
            pacman.image = pacmanDownImage;
        }
        else if (pacman.direction == 'L') {
            pacman.image = pacmanLeftImage;
        }
        else if (pacman.direction == 'R') {
            pacman.image = pacmanRightImage;
        } 
    }

}
