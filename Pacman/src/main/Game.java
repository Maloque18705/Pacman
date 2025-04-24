package main;

import java.util.List;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;


import itf.LoadFont;
import itf.Observer;
import utils.ReadFile;
import entities.Pacman;
import entities.SuperPacGum;
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
import entities.PacGum;

public class Game implements Observer{

    private static List<Entity> entities;
    private static List<Ghosts> ghosts;
    
    private List<List<String>> map;
    private static List<Wall> walls;    
    private static Pacman pacman = null;
    // Implement sounds

    private static boolean firstInput = false;
    public int startTime = 0;
    private boolean win = false;
    private boolean lose = false;
    private int totalFood = 0;

    private static Ghosts pinky, inky, blinky, clyde;


    public Game() {
        init();
    }
    
    private void init() {
        map = new ArrayList<>();
        entities = new ArrayList<>();
        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        
        
        // THIS SECTION IS FOR SOUND 


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
                    entities.add(new PacGum(j * cellSize, i * cellSize));
                    totalFood += 1;
                } else if (map.get(i).get(j).equals("o")) {
                    entities.add(new SuperPacGum(j * cellSize, i * cellSize));
                    totalFood += 1;
                } else if (map.get(i).get(j).equals("P")) {
                    pacman = new Pacman(j * cellSize, i * cellSize);

                    pacman.registerObserver(this);
                    pacman.registerObserver(Main.getTaskbarPanel());
                    
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
        pacman.input(key); // Input trong Pacman
    }


    public void update() {
        if (lose) {

        }

        if (win) {

        }

        if(!win && !lose) {
            for (Entity e : entities) {
                if(!e.isDestroyed()) e.update();
            }
        }
    }
    
    public void draw(Graphics2D g) {
        Font f = LoadFont.loadFont();
        if (f != null) {
            f.deriveFont(10f);
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
            Rectangle2D r = g.getFontMetrics().getStringBounds("LOSE!", g);
            g.drawString("LOSE", (GamePanel.width - (int) r.getWidth())/2, (GamePanel.height - (int) r.getHeight())/2+37);
        } else if (win) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke((float) 1.5));
            Rectangle2D r = g.getFontMetrics().getStringBounds("WIN!", g);
            g.drawString("WIN", (GamePanel.width - (int) r.getWidth())/2, (GamePanel.height - (int) r.getHeight())/2+37);
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


    @Override
    public void updatePacGumEaten(PacGum pg) {
        pg.setDestroyed();
        totalFood -= 1;
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } 
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        spg.setDestroyed();
        totalFood -= 1;

        for (Ghosts ghosts : ghosts) {
            ghosts.getState().superPacGumEaten();
        }
    }

    public static void waitIn3Sec() {
        try {
            Thread.sleep(4800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGhostCollision(Ghosts gh) {
        if (gh.getState() instanceof FrightMode) {
            gh.getState().eaten();
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
