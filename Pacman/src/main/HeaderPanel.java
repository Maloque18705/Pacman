package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import entities.PacPellet;
import entities.SuperPacPellet;
import entities.ghosts.Ghosts;
import entities.ghosts.ghostState.FrightMode;
import itf.LoadFont;

public class HeaderPanel extends JPanel implements itf.Observer{
    private int width;
    private int score;
    private int highScore;
    private Game game;
    private boolean flashRed = false;
    private boolean isHighScoreFlashing = false;
    private Timer flashTimer;

    public HeaderPanel(int width, int score, int highScore, Game game) {
        this.width = width;
        this.score = score;
        this.highScore = readHighScore();
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

        g2d.setColor(isHighScoreFlashing && flashRed ? Color.RED : Color.WHITE);
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
        g2d.drawString(scoreText, (width - textWidth) / 6, 40);
    }

    public void updateHighScore(int currentScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
            highScore = currentScore;
            saveHighScore(highScore);
            System.out.println("Save highscore: " + highScore);
            repaint();
        }
    }

    private int readHighScore() {
        try {
            File file = new File("res/highscore.txt");
            if (!file.exists()) {
                return 0;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return line != null ? Integer.parseInt(line.trim()) : 0;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to read highscore: " + e.getMessage());
            return 0;
        }  
    } 

    private void saveHighScore(int highScore) {
        try {
            File file = new File("res/highscore.txt");
            file.getParentFile().mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setScore(int score) {
        this.score = score;
        if (score > highScore) {
            highScore = score;
            saveHighScore(highScore);
            // startHighScoreFlash();
        }
        repaint();
    }

    public void reset() {
        score = 0;
        repaint();
    }

    private void startHighScoreFlash() {
        isHighScoreFlashing = true;
        flashRed = true;

        flashTimer.cancel();
        flashTimer = new Timer();

        flashTimer.scheduleAtFixedRate(new TimerTask() {
            int flashCount = 0;

            @Override
            public void run() {
                flashRed = !flashRed;
                flashCount++;
                repaint();
                if (flashCount >= 6) { // 6 lần nháy = 3 giây
                    isHighScoreFlashing = false;
                    flashTimer.cancel();
                    flashTimer = new Timer(); // Tạo timer mới cho lần sau
                    repaint();
                }
            }
        }, 0, 500);
    }

    public void updateScore(int s) {
        score += s;
        game.setScore(score); // Đồng bộ với Game
        repaint();
    }

    @Override
    public void updatePacPelletEaten(PacPellet pl) {
        // System.out.println("HeaderPanel: PacPellet eaten, updating score");
        // updateScore(10);
    }

    @Override
    public void updateSuperPacPelletEaten(SuperPacPellet spl) {
        // System.out.println("HeaderPanel: SuperPacPellet eaten, updating score");
        // updateScore(100);
    }

    @Override
    public void updateGhostCollision(Ghosts ghosts) {
        // if (ghosts.getState() instanceof FrightMode) {
        //     System.out.println("HeaderPanel: Ghost eaten, updating score");
        //     updateScore(300);   
        // }
    }
}
