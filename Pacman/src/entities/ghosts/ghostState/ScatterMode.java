package entities.ghosts.ghostState;

import entities.ghosts.Ghosts;
import utils.CheckCollision;
import utils.Utils;

public class ScatterMode extends GhostState{
    public ScatterMode(Ghosts ghosts) {
        super(ghosts);
    }

    @Override 
    public void superPacGumEaten() {
        ghosts.switchFrightMode();
    }

    @Override
    public void timerModeOver() {
        ghosts.switchChaseMode();
    }

    @Override
    public int[] getTargetPosition() {
        return ghosts.getScatterTargetPosition();
    }

    @Override
    public void computerNextDir() {
        int[] position = getTargetPosition();

        int newXSpeed = 0;
        int newYSpeed = 0;

        if (!ghosts.onTheGrid()) return;
        if (!ghosts.onGameplayWindow()) return;

        double minDist = Double.MAX_VALUE;

        if (ghosts.getXSpeed() <= 0 && !CheckCollision.checkWallCollision(ghosts, -ghosts.getSpeed(), 0)) {
            double distance = Utils.getDistance(ghosts.getXPos() - ghosts.getSpeed(), ghosts.getYPos(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = -ghosts.getSpeed();
                newYSpeed = 0;
                minDist = distance;
            }
        }

        if (ghosts.getXSpeed() >= 0 && !CheckCollision.checkWallCollision(ghosts, ghosts.getSpeed(), 0)) {
            double distance = Utils.getDistance(ghosts.getXPos() + ghosts.getSpeed(), ghosts.getYPos(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = ghosts.getSpeed();
                newYSpeed = 0;
                minDist = distance;
            }
        }

        if (ghosts.getYSpeed() <= 0 && !CheckCollision.checkWallCollision(ghosts, 0, -ghosts.getSpeed())) {
            double distance = Utils.getDistance(ghosts.getXPos(), ghosts.getYSpeed() - ghosts.getSpeed(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = 0;
                newYSpeed = -ghosts.getSpeed();
                minDist = distance;
            }
        }

        if (ghosts.getYSpeed() >= 0 && !CheckCollision.checkWallCollision(ghosts, 0, ghosts.getSpeed())) {
            double distance = Utils.getDistance(ghosts.getXPos(), ghosts.getYSpeed() + ghosts.getSpeed(), position[0], position[1]);
            if (distance < minDist) {
                newXSpeed = 0;
                newYSpeed = ghosts.getSpeed();
                minDist = distance;
            }
        }

        if (newXSpeed == 0 && newYSpeed == 0) return;

        if (Math.abs(newXSpeed) != Math.abs(newYSpeed)) {
            ghosts.setXSpeed(newXSpeed);
            ghosts.setYSpeed(newYSpeed);
        } else {
            if (newXSpeed != 0) {
                ghosts.setXSpeed(0);
                ghosts.setYSpeed(newYSpeed);
            } else {
                ghosts.setXSpeed(newXSpeed);
                ghosts.setYSpeed(0);
            }
        }
    }
}
