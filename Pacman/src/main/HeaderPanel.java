package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entities.PacPellet;
import entities.SuperPacPellet;
import entities.ghosts.Ghosts;
import entities.ghosts.ghostState.FrightMode;
import itf.LoadFont;

public class HeaderPanel extends JPanel implements itf.Observer{
    private int width;
    private int score;
    private final int highScore;
    private Game game;

    public HeaderPanel(int width, int score, int highScore, Game game) {
        this.width = width;
        this.score = score;
        this.highScore = highScore;
        this.game = game;
        setPreferredSize(new Dimension(width, 70));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        Font topFont = LoadFont.loadFont();
        if (topFont != null) {
            topFont = topFont.deriveFont(15f);
            g2d.setFont(topFont);
        } else {
            throw new RuntimeException("Font is null");
        }

        g2d.setColor(Color.WHITE);
        FontMetrics metrics = g2d.getFontMetrics(topFont);
        String highScoreText = "HIGHSCORE";
        int textWidth = metrics.stringWidth(highScoreText);
        g2d.drawString(highScoreText, (width - textWidth) / 2, 20);

        String highScoreNum = String.valueOf(highScore);
        textWidth = metrics.stringWidth(highScoreNum);
        g2d.drawString(highScoreNum, (width - textWidth) / 2, 40);

        
        // Draw SCORE
        Font scoreFont = LoadFont.loadFont();
        if (scoreFont != null) {
            scoreFont = scoreFont.deriveFont(15f);
            g2d.setFont(scoreFont);
        } else {
            throw new RuntimeException("Font is null");
        }

        g2d.setColor(Color.WHITE);
        String s = "1UP";
        textWidth = g2d.getFontMetrics(scoreFont).stringWidth(s);
        g2d.drawString(s, (width - textWidth) / 10, 20);

        String scoreText = String.valueOf(score);
        textWidth = g2d.getFontMetrics(scoreFont).stringWidth(scoreText);
        g2d.drawString(scoreText, (width - textWidth) / 10, 40);
    }

    public void setScore(int score) {
        this.score = score;
        repaint();
    }

    public void updateScore(int s) {
        score += s;
        game.setScore(score); // Đồng bộ với Game
        repaint();
    }

    @Override
    public void updatePacPelletEaten(PacPellet pl) {
        System.out.println("HeaderPanel: PacPellet eaten, updating score");
        updateScore(10);
    }

    @Override
    public void updateSuperPacPelletEaten(SuperPacPellet spl) {
        System.out.println("HeaderPanel: SuperPacPellet eaten, updating score");
        updateScore(100);
    }

    @Override
    public void updateGhostCollision(Ghosts ghosts) {
        if (ghosts.getState() instanceof FrightMode) {
            System.out.println("HeaderPanel: Ghost eaten, updating score");
            updateScore(300);   
        }
    }
}
