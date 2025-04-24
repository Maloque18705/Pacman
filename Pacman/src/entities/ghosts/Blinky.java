package entities.ghosts;

import main.Game;
import main.GamePanel;

public class Blinky extends Ghosts{
    public Blinky(int xPos, int yPos) {
        super(xPos, yPos, "blinky.png", 0);
    }

    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        position[0] = Game.getPacman().getXPos();
        position[1] = Game.getPacman().getYPos();
        return position;
    }

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GamePanel.width;
        position[1] = 0; 
        return position; 
    }
}
