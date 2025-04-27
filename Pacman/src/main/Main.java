package main;

import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {

        private static int SCREEN_WIDTH = 448;
        private static int SCREEN_HEIGHT = 596;
        private static int GAME_HEIGHT = 496;
        private static GamePanel gamePanel;
        private static TaskbarPanel taskbarPanel;
        private static HeaderPanel headerPanel;
        private static String name = "PacMan";
        private static JFrame frame;
        private static Game game;
    
        public Main() {
                frame = new JFrame(name);
                game = new Game(null);
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                headerPanel = new HeaderPanel(SCREEN_WIDTH, 0, 1000, game);
                panel.add(headerPanel);
                
                // Add MENU
                Menu menu = new Menu(SCREEN_WIDTH, SCREEN_HEIGHT, frame);
                panel.add(menu);

                frame.add(panel);
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

                // HeaderPanel
                headerPanel = new HeaderPanel(SCREEN_WIDTH, 0, 1000, game);
                panel.add(headerPanel);


                // GamePanel
                gamePanel = new GamePanel(SCREEN_WIDTH, GAME_HEIGHT);
                panel.add(gamePanel);

                // TaskbarPanel
                taskbarPanel = new TaskbarPanel(SCREEN_WIDTH, 80, game);
                panel.add(taskbarPanel);
                
                game = new Game(taskbarPanel);
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

        public static HeaderPanel getHeaderPanel() {
                return headerPanel;
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