package main;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Button extends JFrame {
    Button() {
        JButton startButton = new JButton();
        startButton.setBounds(200, 100, 100, 50);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(startButton);
    }
}
