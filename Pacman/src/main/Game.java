package main;

import java.util.List;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;


import itf.LoadFont;
import itf.Observer;
import utils.Intro;
import utils.PacPelletEatenSound;
import utils.Pacman_Eliminated;
import utils.ReadFile;
import utils.Win;
import entities.Pacman;
import entities.SuperPacPellet;
import entities.Wall;
import entities.ghosts.Blinky;
import entities.ghosts.Clyde;
import entities.ghosts.Ghosts;
import entities.ghosts.Inky;
import entities.ghosts.Pinky;
import entities.ghosts.ghostState.EatenMode;
import entities.ghosts.ghostState.FrightMode;
import inputs.KeyboardInputs;
import entities.Entity;
import entities.GhostShed;
import entities.PacPellet;

public class Game implements Observer{

    private static List<Entity> entities;
    private static List<Ghosts> ghosts;
    
    private List<List<String>> map;
    private static List<Wall> walls;    
    private static Pacman pacman = null;
    // Implement sounds
    private static PacPelletEatenSound pacPelletEatenSound;
    private static Win winSound;
    private static Pacman_Eliminated pacmanEliminatedSound;

    private static boolean firstInput = false;
    public int startTime = 0;
    private boolean win = false;
    private boolean lose = false;
    private int totalFood = 0;
    private int score = 0;
    private int highScore = 0; 
    private boolean loseSoundPlayed = false;

    private static Ghosts pinky, inky, blinky, clyde;
    private final TaskbarPanel taskbarPanel;


    public Game(TaskbarPanel taskbarPanel) {
        this.taskbarPanel = taskbarPanel;
        System.out.println("New Game instance: " + this);
        init();
    }
    
    private void init() {
        map = new ArrayList<>();
        entities = new ArrayList<>();
        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        
        
        // THIS SECTION IS FOR SOUND 
        pacPelletEatenSound = new PacPelletEatenSound();
        pacmanEliminatedSound = new Pacman_Eliminated();
        winSound = new Win();

        highScore = readHighScore();

        try {
            map = ReadFile.readMap(Objects.requireNonNull(getClass().getClassLoader().getResource("res/level/level.csv")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        int cellsPerRow = map.get(0).size();
        int cellsPerCol = map.size();

        int cellSize = 8;

        // THIS SECTION IS FOR MAP LOGIC

        for (int i = 0; i < cellsPerCol; i++) {
            for (int j = 0; j < cellsPerRow; j++) {
                if (map.get(i).get(j).equals(".")) {
                    entities.add(new PacPellet(j * cellSize, i * cellSize));
                    totalFood += 1;
                } else if (map.get(i).get(j).equals("o")) {
                    entities.add(new SuperPacPellet(j * cellSize, i * cellSize));
                    totalFood += 1;
                } else if (map.get(i).get(j).equals("P")) {
                    pacman = new Pacman(j * cellSize, i * cellSize);

                    pacman.registerObserver(this);
                    pacman.registerObserver(Main.getTaskbarPanel());
                    pacman.registerObserver(Main.getHeaderPanel());
                    
                } else if (map.get(i).get(j).equals("x")) {
                    entities.add(new Wall (j * cellSize, i * cellSize));
                } else if (map.get(i).get(j).equals("-")) {
                    entities.add(new GhostShed(j * cellSize, i * cellSize));
                } else if (map.get(i).get(j).equals("b")) {
                    blinky = new Blinky(j * cellSize, i * cellSize);
                } else if (map.get(i).get(j).equals("p")) {
                    pinky = new Pinky(j * cellSize, i * cellSize);
                } else if (map.get(i).get(j).equals("i")) {
                    inky = new Inky(j * cellSize, i * cellSize);
                } else if (map.get(i).get(j).equals("c")) {
                    clyde = new Clyde(j *cellSize, i * cellSize);
                }
            }
        }
        entities.add(pacman);
        entities.add(blinky);
        entities.add(clyde);
        entities.add(inky);
        entities.add(pinky);

        for (Entity e: entities) {
            if (e instanceof Wall) {
                walls.add((Wall) e);
            } else if (e instanceof Ghosts) {
                ghosts.add((Ghosts) e);
            }
        }
    }

    public void input(KeyboardInputs key) {
        if (lose && key.keySpace.isPressed) {
            System.out.println("Game: Spacebar pressed, restarting game");
            reset();
        } else if (key.keyESC.isPressed) {
            System.out.println("RETURN TO MENU");
            Main.returnToMenu();
        } else {
            pacman.input(key);
        }
    }


    public void update() {
        if (lose) {
            if (score > highScore) {
                highScore = score;
                saveHighScore(highScore);
                // Main.getTaskbarPanel();
            }
            if (!loseSoundPlayed) {
                pacPelletEatenSound.stop();
                try {
                    Thread.sleep(200); 
                } catch (InterruptedException e) {
                    System.err.println("Interrupted while waiting: " + e.getMessage());
                }
                pacmanEliminatedSound.play();
                loseSoundPlayed = true;
            } 
        }

        else if (totalFood ==0 ) {
            win = true;
        }

        else if (win) {
            pacPelletEatenSound.stop();
            try {
                Thread.sleep(200); // Chờ 50ms để đảm bảo âm thanh ăn dừng
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting: " + e.getMessage());
            }
            winSound.play();
        }

        else if(!win && !lose) {
            for (Entity e : entities) {
                if(!e.isDestroyed()) e.update();
            }
        }
    }
    
    public void draw(Graphics2D g) {
        Font f = LoadFont.loadFont();
        if (f != null) {
            f.deriveFont(15f);
            g.setFont(f);
        } else {
            throw new RuntimeException("Font is null");
        }
        

        if (!win && !lose) {
            for (Entity e : entities) {
                if (!e.isDestroyed()) e.draw(g);
            }

            if (!firstInput && (startTime % 20 < 10)) {
                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke((float) 1.5));
                Rectangle2D r = g.getFontMetrics().getStringBounds("READY!", g);
                g.drawString("READY", (GamePanel.width - (int) r.getWidth())/2, (GamePanel.height - (int) r.getHeight())/2+37);
            }
        } else if (lose) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke((float) 1.5));
            Rectangle2D r = g.getFontMetrics().getStringBounds("GAME OVER!", g);
            g.drawString("GAME OVER", (GamePanel.width - (int) r.getWidth())/2, (GamePanel.height - (int) r.getHeight())/2+37);
            showRestartPrompt(g);

        } else if (win) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke((float) 1.5));
            Rectangle2D r = g.getFontMetrics().getStringBounds("WIN!", g);
            g.drawString("WIN", (GamePanel.width - (int) r.getWidth())/2, (GamePanel.height - (int) r.getHeight())/2+37);
            showRestartPrompt(g);
        }

        

    }

    public void cleanup() {
        pacPelletEatenSound.close();
        pacmanEliminatedSound.close();
        winSound.close();
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
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to save highscore: " + e.getMessage());
        }
    }

    public void showRestartPrompt(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.setFont(g.getFont().deriveFont(8f)); // Font nhỏ hơn cho thông báo
        String prompt = "Press Spacebar to restart";
        Rectangle2D r = g.getFontMetrics().getStringBounds(prompt, g);
        g.drawString(prompt, (GamePanel.width - (int) r.getWidth()) / 2, (GamePanel.height - (int) r.getHeight()) / 2 + 50);
        System.out.println("Game: Showing restart prompt");
    }

    public void reset() {
        System.out.println("Game: Resetting game");
        lose = false;
        win = false;
        totalFood = 0;
        score = 0;
        entities.clear();
        walls.clear();
        ghosts.clear();
        init();
        taskbarPanel.reset();
        if (Main.getHeaderPanel() != null) {
            Main.getHeaderPanel().setScore(0);
        }
    }


    public static List<Entity> getEntities() {
        return entities;
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public static Pacman getPacman() {
        return pacman;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }

    public static void setFirstInput(boolean b) {
       firstInput = b; 
    }

    public static Ghosts getBlinky() {
        return blinky;
    }

    public static int getLives() {
        return pacman.getLives();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (Main.getHeaderPanel() != null) {
            Main.getHeaderPanel().setScore(score);
        }
    }

    public int getHighScore() {
        return highScore;
    }


    @Override
    public void updatePacPelletEaten(PacPellet pl) {
        pl.setDestroyed();
        totalFood -= 1;
        score += 10;
        pacPelletEatenSound.play();
        if (Main.getHeaderPanel() != null) {
            Main.getHeaderPanel().setScore(score);
        }
    }

    @Override
    public void updateSuperPacPelletEaten(SuperPacPellet spl) {
        spl.setDestroyed();
        totalFood -= 1;
        score += 100;
        pacPelletEatenSound.play();
        for (Ghosts ghosts : ghosts) {
            ghosts.getState().superPacPelletEaten();
        }
        if (Main.getHeaderPanel() != null) {
            Main.getHeaderPanel().setScore(score);
        }
    }

        public static void waitIn3Sec() {
            try {
                Intro i = new Intro();
                i.play();
                Thread.sleep(3000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    @Override
    public void updateGhostCollision(Ghosts gh) {
        if (gh.getState() instanceof FrightMode) {
            gh.getState().eaten();
            score += 300;
            if (Main.getHeaderPanel() != null) {
                Main.getHeaderPanel().setScore(score);
            }
        } else if (!(gh.getState() instanceof EatenMode)) {
            Main.getTaskbarPanel().setLives();
            pacman.reset();
            for (Ghosts g : ghosts) {
                g.reset();
            }
            if (Main.getTaskbarPanel().getLives() == 0) {
                lose = true;
            }
        }
    }
}
