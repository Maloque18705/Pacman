package entities.ghosts;

import main.Game;
import utils.Utils;

public class Pinky extends Ghosts {
    public Pinky(int xPos, int yPos) {
        super(xPos, yPos, "pinky.png", 1);
    }

    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getXPos(), Game.getPacman().getYPos(), 64, Utils.directionConverter(Game.getPacman().getDirection()));
        position[0] = pacmanFacingPosition[0];
        position[1] = pacmanFacingPosition[1];
        return position;
    }

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = 1;
        return position;
    }
}
