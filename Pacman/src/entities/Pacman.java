package entities;

import java.util.ArrayList;
import java.util.List;

import itf.*;
import main.Game;
import utils.CheckCollision;
import inputs.KeyboardInputs;
import entities.ghosts.Ghosts;

public class Pacman extends MovingEntity implements Notify {

    private final List<Observer> observerCollection = new ArrayList<>();
    private int lives = 3;

    public Pacman(int xPos, int yPos) {
        super(xPos, yPos, 32, 2, "pacman.png", 4, 0.2f);
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null) {
            observerCollection.add(observer);
            System.out.println("Pacman: Registered observer: " + observer.getClass().getSimpleName());
        } else {
            System.err.println("Pacman: Attempted to register null observer");
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);
        System.out.println("Pacman: Removed observer: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObserverPacGumEaten(PacGum pg) {
        System.out.println("Pacman: Notifying PacGum eaten, observers: " + observerCollection.size());
        for (Observer o : observerCollection) {
            System.out.println("Pacman: Notifying observer: " + o.getClass().getSimpleName());
            o.updatePacGumEaten(pg);
        }
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        System.out.println("Pacman: Notifying SuperPacGum eaten, observers: " + observerCollection.size());
        for (Observer o : observerCollection) {
            System.out.println("Pacman: Notifying observer: " + o.getClass().getSimpleName());
            o.updateSuperPacGumEaten(spg);
        }
    }

    @Override
    public void notifyObserverGhostsCollision(Ghosts ghosts) {
        System.out.println("Pacman: Notifying Ghost collision, observers: " + observerCollection.size());
        for (Observer o : observerCollection) {
            System.out.println("Pacman: Notifying observer: " + o.getClass().getSimpleName());
            o.updateGhostCollision(ghosts);
        }
    }

    public void input(KeyboardInputs k) {
        int newXSpeed = 0;
        int newYSpeed = 0;
        if (!onTheGrid()) return;
        if (!onGameplayWindow()) return;

        if (k.keyLeft.isPressed && xSpeed >= 0 && !CheckCollision.checkWallCollision(this, -speed, 0)) {
            newXSpeed = -speed;
        }

        if (k.keyRight.isPressed && xSpeed <= 0 && !CheckCollision.checkWallCollision(this, speed, 0)) {
            newXSpeed = speed;
        }

        if (k.keyUp.isPressed && ySpeed >= 0 && !CheckCollision.checkWallCollision(this, 0, -speed)) {
            newYSpeed = -speed;
        }

        if (k.keyDown.isPressed && ySpeed <= 0 && !CheckCollision.checkWallCollision(this, 0, speed)) {
            newYSpeed = speed;
        }

        if (newXSpeed == 0 && newYSpeed == 0) return;

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
            System.out.println("Pacman: Detected PacGum collision");
            notifyObserverPacGumEaten(pg);
        }

        SuperPacGum spg = (SuperPacGum) CheckCollision.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            System.out.println("Pacman: Detected SuperPacGum collision");
            notifyObserverSuperPacGumEaten(spg);
        }

        Ghosts ghosts = (Ghosts) CheckCollision.checkCollision(this, Ghosts.class);
        if (ghosts != null) {
            System.out.println("Pacman: Detected Ghost collision");
            notifyObserverGhostsCollision(ghosts);
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