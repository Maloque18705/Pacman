package entities.ghosts;

import main.Game;
import main.GamePanel;
import utils.Utils;

public class Inky extends Ghosts {
    public Inky(int xPos, int yPos) {
        super(xPos, yPos, "inky.png", 3);
    }

    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getXPos(), Game.getPacman().getYPos(), 32d, Utils.directionConverter(Game.getPacman().getDirection()));
        double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition[0], pacmanFacingPosition[1], Game.getBlinky().getXPos(), Game.getBlinky().getYPos());
        double directionOtherGhost = Utils.getDistance(Game.getBlinky().getXPos(), Game.getBlinky().getYPos(), pacmanFacingPosition[0], pacmanFacingPosition[1]);
        int[] blinkyVectorPosition = Utils.getPointDistanceDirection(pacmanFacingPosition[0], pacmanFacingPosition[1], distanceOtherGhost, directionOtherGhost);
        position[0] = blinkyVectorPosition[0];
        position[1] = blinkyVectorPosition[1];
        return position;
    } 

    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GamePanel.width;
        position[1] = GamePanel.height;
        return position;
    }
}
