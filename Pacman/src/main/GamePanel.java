package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
public class GamePanel extends JPanel implements Runnable {

    public static int width;
    public static int height;

    private Thread thread;
    private Image background;
    private static Game game;
    private BufferedImage bufferedImage;
    private Graphics2D g;

    private KeyboardInputs k;
    private static boolean running;

    public GamePanel(int width, int height) {
        GamePanel.width = width;
        GamePanel.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        

        // THIS SECTION FOR LOADING BACKGROUND
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/img/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e); 
        }
    }

    public void update() {
        game.update();
    }

    public void init() {
        running =true;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) bufferedImage.getGraphics();
        game = new Game();

        k = new KeyboardInputs(this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(bufferedImage, 0, 0, width, height, null);
        g2.dispose(); 
    }

    public void render() {
        if (g != null) {
            g.drawImage(background, 0, 0, width, height, null);
            game.draw(g);
        }
    }

    public void input() {
        game.input(k);
    }

    @Override
    public void run() {
        init();
        final double FPS = 60.0;
        final double TBU = 1e9/FPS;
        final int MUBR = 5;
        double startTime = System.nanoTime();

        while(running) {
            // k.keyLeft.keyState();
            double now = System.nanoTime();
            int updateCount = 0;
            while((now - startTime > TBU) && (updateCount < MUBR)) {
                input();
                update();
                startTime += TBU;
                updateCount++;
            }
            if (now - startTime > TBU) {
                startTime = now - TBU;
            }

            render();
            draw();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void setRunning(boolean running) {
        GamePanel.running = running;
    }
}
