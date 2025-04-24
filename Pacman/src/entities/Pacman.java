package entities;

import java.util.ArrayList;
import java.util.List;

import itf.*;
import main.Game;
import utils.CheckCollision;
import inputs.KeyboardInputs;
import itf.Notify;

import entities.ghosts.Ghosts;

public class Pacman extends MovingEntity implements Notify {

    private final List<Observer> observerCollection = new ArrayList<>();
    private int lives = 3;

    public Pacman(int xPos, int yPos) {
        super(xPos, yPos, 32, 2, "pacman.png", 4, 0.2f);
    }

    public void input(KeyboardInputs k) {
        int newXSpeed = 0;
        int newYSpeed = 0;
        if(!onTheGrid()) return;
        if(!onGameplayWindow()) return;

        if(k.keyLeft.isPressed && xSpeed >= 0 && !CheckCollision.checkWallCollision(this, -speed, 0)) {
            newXSpeed = -speed;
        } 

        if (k.keyRight.isPressed && xSpeed <= 0  && !CheckCollision.checkWallCollision(this, speed, 0)) {
            newXSpeed = speed;
        }

        if (k.keyUp.isPressed && ySpeed >= 0 && !CheckCollision.checkWallCollision(this, 0, -speed)) {
            newYSpeed = -speed;
        }

        if (k.keyDown.isPressed && ySpeed <= 0 && !CheckCollision.checkWallCollision(this, 0, speed)) {
            newYSpeed = speed;
        }
        
        if (newXSpeed == 0  && newYSpeed == 0) return;

        if (Math.abs(newXSpeed) != Math.abs(newYSpeed)) {
            xSpeed = newXSpeed;
            ySpeed = newYSpeed;
        } else {
            if (xSpeed != 0) {
                xSpeed = 0;
                ySpeed = newYSpeed;
            } else {
                xSpeed = newXSpeed;
                ySpeed = 0;
            }
        }
    }

    public void update() {
        if (!Game.getFirstInput()) {
            Game.waitIn3Sec();
            Game.setFirstInput(true);
        }

        if (!CheckCollision.checkWallCollision(this, xSpeed, ySpeed)) {
            updatePos();
        }

        PacGum pg = (PacGum) CheckCollision.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);
        }

        SuperPacGum spg = (SuperPacGum) CheckCollision.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            notifyObserverSuperPacGumEaten(spg);
        }

        Ghosts ghosts = (Ghosts) CheckCollision.checkCollision(this, Ghosts.class);
        if (ghosts != null) {
            notifyObserverGhostsCollision(ghosts);
        }
    }

    @Override 
    public void registerObserver(Observer observer) {
        observerCollection.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);
    }

    @Override 
    public void notifyObserverPacGumEaten(PacGum pg) {
        for (Observer o : observerCollection) {
            o.updatePacGumEaten(pg);
        }
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        for (Observer o : observerCollection) {
            o.updateSuperPacGumEaten(spg);
        } 
    }

    @Override 
    public void notifyObserverGhostsCollision(Ghosts ghosts) {
        for (Observer o : observerCollection) {
            o.updateGhostCollision(ghosts);
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
