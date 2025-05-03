package main;
import javax.imageio.ImageIO;
import javax.swing.*;

import itf.LoadFont;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Menu extends JPanel {
    
    private final int width;
    private final int height;
    private final JFrame frame;
    private final String[] options = {"START", "HOW TO PLAY", "QUIT"};
    private int selectedOption = 0;
    private BufferedImage image;

    private final Timer blinkTimer;
    private boolean blinkVisible = true;

    public Menu(int width, int height, JFrame frame) {
        this.width = width;
        this.height = height;
        this.frame = frame;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();


        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/pacmanLogo.png")));
            image = resizeImage(image, 400, 496);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        blinkTimer = new Timer(500, e -> {
            blinkVisible = !blinkVisible;
            repaint();
        });
        blinkTimer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    selectedOption = (selectedOption -1 + options.length) % options.length;
                    repaint();
                }   else if (keyCode == KeyEvent.VK_DOWN) {
                    selectedOption = (selectedOption + 1) % options.length;
                    repaint();
                } else if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE) {
                    
                    if (options[selectedOption].equals("START")) {
                        System.out.println("START SELECTED");
                        // LOAD GAMEPANEL
                        blinkTimer.stop();
                        Main.startGame();
                    
                    } else if (options[selectedOption].equals("HOW TO PLAY")) {
                        JOptionPane.showMessageDialog(frame,
                            "Hướng dẫn chơi Pacman:\n"
                            + "- Sử dụng các phím mũi tên để di chuyển Pacman.\n"
                            + "- Ăn hết các hạt chấm nhỏ để qua màn.\n"
                            + "- Tránh các con ma. Nếu bị bắt, bạn sẽ mất một mạng.\n"
                            + "- Ăn Pac-Pellet lớn để có thể ăn ngược lại ma!",
                            "Hướng Dẫn Chơi",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else if (options[selectedOption].equals("QUIT")) {
                        blinkTimer.stop();
                        System.exit(0);
                    }   
                }
            }
        });
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
        int newWidth = targetWidth;
        int newHeight = (int) (targetWidth/aspectRatio);
        
        if (newHeight > targetHeight) {
            newHeight = targetHeight;
            newWidth = (int) (targetHeight * aspectRatio);
        }
        
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; 
        
        // Draw LOGO
        if (image != null) {
            int x = (width - image.getWidth()) / 2 - 5;
            int y = (height - image.getHeight()) / 2 - 100;
            g2d.drawImage(image, x, y, this);
        }
 

        // Draw OPTIONS
        Font optionsFont = LoadFont.loadFont();
        if (optionsFont != null) {
            optionsFont = optionsFont.deriveFont(20f);
            g2d.setFont(optionsFont);
        } else {
            throw new RuntimeException("Font is null");
        }
        // g2d.setFont(optionsFont);
        int optionY = height / 2 + 50;
        for (int i = 0; i < options.length; i++) {
            int optionTextWidth = g2d.getFontMetrics(optionsFont).stringWidth(options[i]);
            int textX = (width - optionTextWidth) /2;

            if (i == selectedOption) {

                if (blinkVisible) {
                    g2d.setColor(Color.YELLOW);
                    int[] xPoints = {textX - 40, textX - 10, textX - 40};
                    int[] yPoints = {optionY - 20, optionY - 10, optionY};
                    g2d.fillPolygon(xPoints, yPoints, 3);
                }
                g2d.setColor(Color.YELLOW);
            } else {
                g2d.setColor(Color.WHITE);
            } 
            g2d.drawString(options[i], textX, optionY);
            optionY += 40;
        }

    }
}