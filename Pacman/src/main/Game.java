package main;

import entities.Pacman;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Pacman pacman;

    public final static int GAME_WIDTH =
    public final static int GAME_HEIGHT =  



    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
    }
}
