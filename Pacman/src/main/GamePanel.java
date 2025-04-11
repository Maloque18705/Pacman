package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
public class GamePanel extends JPanel {
    public GamePanel() {

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        // Draw Pacman
        g.drawImage()

        // Draw Ghost
        for (Block ghost: ghosts) {
            g.drawImage();
        }

        // Draw Walls
        for (Block wall: walls) {
            g.drawImage();
        }
        g.setColor(Color.WHITE); 

        // Draw Foods
        for (Block food: foods) {
            g.fillRect();
        }

        // Game Font
        g.setFont(new Font("Arial", Font.PLAIN, 18));
    }
}
