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
    public void notifyObserverPacPelletEaten(PacPellet pl) {
        System.out.println("Pacman: Notifying PacPellet eaten, observers: " + observerCollection.size());
        for (Observer o : observerCollection) {
            System.out.println("Pacman: Notifying observer: " + o.getClass().getSimpleName());
            o.updatePacPelletEaten(pl);
        }
    }

    @Override
    public void notifyObserverSuperPacPelletEaten(SuperPacPellet spl) {
        System.out.println("Pacman: Notifying SuperPacPellet eaten, observers: " + observerCollection.size());
        for (Observer o : observerCollection) {
            System.out.println("Pacman: Notifying observer: " + o.getClass().getSimpleName());
            o.updateSuperPacPelletEaten(spl);
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

        PacPellet pl = (PacPellet) CheckCollision.checkCollision(this, PacPellet.class);
        if (pl != null) {
            System.out.println("Pacman: Detected PacPellet collision");
            notifyObserverPacPelletEaten(pl);
        }

        SuperPacPellet spl = (SuperPacPellet) CheckCollision.checkCollision(this, SuperPacPellet.class);
        if (spl != null) {
            System.out.println("Pacman: Detected SuperPacPellet collision");
            notifyObserverSuperPacPelletEaten(spl);
        }

        Ghosts ghosts = (Ghosts) CheckCollision.checkCollision(this, Ghosts.class);
        if (ghosts != null) {
            System.out.println("Pacman: Detected Ghost collision");
            notifyObserverGhostsCollision(ghosts);
        }
    }

    public void setLive(int lives) {
        this.lives = lives;
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