package entities.ghosts;

import main.Game;
import main.GamePanel;
import utils.Utils;

public class Clyde extends Ghosts {
    public Clyde(int xPos, int yPos) {
        super(xPos, yPos, "clyde.png", 3);
    }

    @Override
    public int[] getChaseTargetPosition() {
        if (Utils.getDistance(this.getXPos(), this.getYPos(), Game.getPacman().getXPos(), Game.getPacman().getYPos()) < 64) {
            int[] position = new int[2];
            position[0] = Game.getPacman().getXPos();
            position[1] = Game.getPacman().getYPos();
            return position;
        } else {
            return getScatterTargetPosition();
        }
    }

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = GamePanel.height;
        return position;
    }
    
}
