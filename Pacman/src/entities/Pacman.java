package entities;

import entities.ghosts.Ghosts;
import inputs.KeyboardInputs;
import itf.*;
import java.util.ArrayList;
import java.util.List;
import main.Game;
import utils.CheckCollision;

public class Pacman extends MovingEntity {

    private final List<Observer> observerCollection = new ArrayList<>();
    private int lives = 3;

    public Pacman(int xPos, int yPos) {
        super(xPos, yPos, 32, 2, "pacman.png", 4, 0.2f);
    }

    public void input(KeyboardInputs k) {
        int newXSpeed = 0;
        int newYSpeed = 0;

        if (!onTheGrid())
            return; // Chỉ xử lý khi Pacman ở trên lưới
        if (!onGameplayWindow())
            return; // Chỉ xử lý khi Pacman trong cửa sổ trò chơi

        if (k.keyLeft.isPressed && !CheckCollision.checkWallCollision(this, -speed, 0)) {
            newXSpeed = -speed;
            newYSpeed = 0;
        } else if (k.keyRight.isPressed && !CheckCollision.checkWallCollision(this, speed, 0)) {
            newXSpeed = speed;
            newYSpeed = 0;
        } else if (k.keyUp.isPressed && !CheckCollision.checkWallCollision(this, 0, -speed)) {
            newXSpeed = 0;
            newYSpeed = -speed;
        } else if (k.keyDown.isPressed && !CheckCollision.checkWallCollision(this, 0, speed)) {
            newXSpeed = 0;
            newYSpeed = speed;
        }

        // Cập nhật tốc độ di chuyển
        xSpeed = newXSpeed;
        ySpeed = newYSpeed;
    }

    @Override
    public void update() {
        if (!Game.getFirstInput()) {
            Game.setFirstInput(true);
        }

        if (!CheckCollision.checkWallCollision(this, xSpeed, ySpeed)) {
            updatePos();
        }

        PacGum pg = (PacGum) CheckCollision.checkCollision(this, PacGum.class);
        if (pg != null) {

        }

        SuperPacGum spg = (SuperPacGum) CheckCollision.checkCollision(this, SuperPacGum.class);
        if (spg != null) {

        }

        Ghosts ghosts = (Ghosts) CheckCollision.checkCollision(this, Ghosts.class);
        if (ghosts != null) {

        }
    }

    public void setLives() {
        if (lives > 1) {
            lives -= 1;
        }
    }

    public int getLives() {
        return lives;
    }

    public void reset() {
        super.reset();
    }
}
