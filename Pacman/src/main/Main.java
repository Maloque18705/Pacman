package main;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {

        private static int SCREEN_WIDTH = 448;
        private static int SCREEN_HEIGHT = 496;
        private static GamePanel gamePanel;
        private static TaskbarPanel taskbarPanel;
        private static String name = "PacMan";
    
        public Main() {
                JFrame jFrame = new JFrame(name);
                JPanel jPanel = new JPanel();

                gamePanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGHT);
                taskbarPanel = new TaskbarPanel(SCREEN_WIDTH, SCREEN_HEIGHT/15);

                jPanel.add(gamePanel);
                jPanel.add(taskbarPanel);
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
                jFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/icon.png"))).getImage());
                jFrame.add(jPanel);
                jFrame.pack();
                jFrame.setLocationRelativeTo(null);
                jFrame.setResizable(false);
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jFrame.setVisible(true);
        
        }
        

        public static GamePanel getGamePanel() {
                return gamePanel;
        }

        public static TaskbarPanel getTaskbarPanel() {
                return taskbarPanel;
        }

        public static void main(String[] args) {
               new Main(); 
        } 
}