package entities.ghosts;

import entities.ghosts.ghostState.*;
import java.util.List;
import main.Game;
import main.GamePanel;
import utils.CheckCollision;
import utils.Utils;

public class Blinky extends Ghosts {
    private static final int GRID_SIZE = 8; // Adjusted to match the map's cell size

    public Blinky(int xPos, int yPos) {
        super(xPos, yPos, "blinky.png", 0);
    }

    @Override
    public void update() {
        if (!onTheGrid()) {
            updatePos(); // Continue moving until aligned with the grid
            return;
        }

        if (state != null) {
            // Handle timers for state transitions
            if (state instanceof FrightMode && frightTimer >= 600) { // 10 seconds for FrightMode
                state.timeFrightModeOver();
            } else if (!(state instanceof ScatterMode) && !(state instanceof ChaseMode) || modeTimer < 1200) {
                // No action needed
            } else {
                // Switch between Chase and Scatter modes
                state.timerModeOver();
                isChasing = !isChasing;
                modeTimer = 0;
            }

            // Compute next direction based on state
            if (state instanceof ChaseMode) {
                // Use A* pathfinding for ChaseMode
                int currentRow = this.getYPos() / GRID_SIZE;
                int currentCol = this.getXPos() / GRID_SIZE;
                int targetRow = getChaseTargetPosition()[1] / GRID_SIZE;
                int targetCol = getChaseTargetPosition()[0] / GRID_SIZE;

                int[][] grid = GamePanel.getGrid(); // Retrieve the grid from GamePanel
                List<int[]> path = AStar.findPath(grid, currentRow, currentCol, targetRow, targetCol);

                if (path != null && path.size() > 1) {
                    int[] nextStep = path.get(1);
                    int nextRow = nextStep[0];
                    int nextCol = nextStep[1];

                    int targetX = nextCol * GRID_SIZE;
                    int targetY = nextRow * GRID_SIZE;

                    if (targetX > this.getXPos() && !CheckCollision.checkWallCollision(this, this.getSpeed(), 0)) {
                        setXSpeed(this.getSpeed());
                        setYSpeed(0);
                    } else if (targetX < this.getXPos() && !CheckCollision.checkWallCollision(this, -this.getSpeed(), 0)) {
                        setXSpeed(-this.getSpeed());
                        setYSpeed(0);
                    } else if (targetY > this.getYPos() && !CheckCollision.checkWallCollision(this, 0, this.getSpeed())) {
                        setXSpeed(0);
                        setYSpeed(this.getSpeed());
                    } else if (targetY < this.getYPos() && !CheckCollision.checkWallCollision(this, 0, -this.getSpeed())) {
                        setXSpeed(0);
                        setYSpeed(-this.getSpeed());
                    }
                } else {
                    randomMove(); // Move randomly if no path is found
                }
            } else {
                // Use default state logic for other modes
                state.computerNextDir();
            }
        }

        updatePos();
    }

    private void randomMove() {
        int direction = Utils.randomInt(4); // 0: up, 1: down, 2: left, 3: right
        int speed = this.getSpeed();
        switch (direction) {
            case 0 -> {
                setXSpeed(0);
                setYSpeed(-speed);
            }
            case 1 -> {
                setXSpeed(0);
                setYSpeed(speed);
            }
            case 2 -> {
                setXSpeed(-speed);
                setYSpeed(0);
            }
            case 3 -> {
                setXSpeed(speed);
                setYSpeed(0);
            }
        }
    }

    @Override
    public int[] getChaseTargetPosition() {
        return new int[] { Game.getPacman().getXPos(), Game.getPacman().getYPos() };
    }

    @Override
    public int[] getScatterTargetPosition() {
        return new int[] { GamePanel.width, 0 };
    }
}