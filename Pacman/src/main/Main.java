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
        private static JFrame frame;
        private static Game game;
    
        public Main() {
                frame = new JFrame(name);

                game = new Game(null);

                Menu menu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, frame);
                frame.add(menu);
                frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/icon.png"))).getImage());
                frame.pack();
                
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                // Game.waitIn3Sec();
        
        }
        
        public static void startGame() {
                frame.getContentPane().removeAll();
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                gamePanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGHT);
                taskbarPanel = new TaskbarPanel(SCREEN_WIDTH, SCREEN_HEIGHT/9, game);
                panel.add(gamePanel);
                // panel.add(taskbarPanel);
                frame.add(panel);
                frame.revalidate();
                frame.repaint();
                gamePanel.requestFocus();
        }

        public static Game getGame() {
                return game;
        }

        public static GamePanel getGamePanel() {
                return gamePanel;
        }

        public static TaskbarPanel getTaskbarPanel() {
                return taskbarPanel;
        }

        public static void returnToMenu() {
                frame.getContentPane().removeAll();
                Menu menu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, frame);
                frame.add(menu);
                frame.revalidate();
                frame.repaint();
                menu.requestFocus();
        }

        public static void main(String[] args) {
               new Main(); 
        } 
}