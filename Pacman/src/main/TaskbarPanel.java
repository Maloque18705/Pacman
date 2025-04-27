package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.PacPellet;
import entities.SuperPacPellet;
import entities.ghosts.Ghosts;
import entities.ghosts.ghostState.FrightMode;
import itf.LoadFont;
import itf.Observer;

public class TaskbarPanel extends JPanel implements Observer{
    private int width;
    private int height;
    private Font font;

    private BufferedImage pacman;
    private int lives = 3;

    private Game game;

    public TaskbarPanel(int width, int height, Game game) {
        this.width = width;
        this.height = height;
        this.game = game;

        setPreferredSize(new Dimension(width, height));

        font = LoadFont.loadFont();
        
        try {
            pacman = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("./res/img/pacman.png")));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        lives = 3;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        int size = 20;
        for (int i = 0; i < lives; i++) {
            g.drawImage(pacman.getSubimage(32, 0, 32, 32), 300 + (i*30), (height-size)/2, size, size, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(font);
    }


    public void updateScore(int s) {
        int temp = game.getScore();
        temp = temp + s;
        game.setScore(temp);
    }

    @Override
    public void updatePacPelletEaten(PacPellet pl) {
        System.out.println("TaskbarPanel: PacPellet eaten, requesting repaint");
        updateScore(10);
        // revalidate();
        repaint();
    }

    @Override
    public void updateSuperPacPelletEaten(SuperPacPellet spl) {
        System.out.println("TaskbarPanel: SuperPacPellet eaten, requesting repaint");
        updateScore(100);
        // revalidate();
        repaint();
    }

    @Override
    public void updateGhostCollision(Ghosts ghosts) {
        if (ghosts.getState() instanceof FrightMode)  {
            System.out.println("TaskbarPanel: Ghost eaten, requesting repaint");
            updateScore(300);
            // revalidate();
            repaint();
        }
    }

    public void setLives() {
        if (lives>0) {
            lives -= 1;
        }
        revalidate();
        repaint();
    }

    public int getLives() {
        return lives;
    }

    public void reset() {
        lives = 3;
        // game.setScore(0);
        // revalidate();
        repaint();
    }
}
