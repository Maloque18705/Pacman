import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Testing extends JFrame {
    
    private static final int SCREEN_HEIGHT = 496;
    private static final int SCREEN_WIDTH = 448;
    private static final String NAME = "Test";
    private final String[] options = {"START", "SETTINGS", "QUIT"};
    private int selectedOption = 0;
    private BufferedImage image;
    
    // Biến cho drawScore và showIntroScreen
    private int score = 0; // Điểm số
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 15);
    private final Font optionsFont = new Font("Helvetica", Font.BOLD, 30);
    private int highScore = 1000;

    public Testing() {
        setTitle(NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null); 
        setResizable(false);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("res/pacmanLogo.png")));
            image = resizeImage(image, 400, 496);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        setVisible(true);
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

    // JPanel tùy chỉnh để vẽ
    private class GamePanel extends JPanel {
        private boolean blinkVisible = true;
        private final Timer blinkTimer;

        public GamePanel() {
            // Đặt kích thước ưu tiên cho GamePanel
            setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
            setBackground(Color.BLACK); 

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
                        } else if (options[selectedOption].equals("QUIT")) {
                            System.exit(0);
                        } else if (options[selectedOption].equals("SETTINGS")) {
                            System.out.println("SETTINGS selected");
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setFont(smallFont);
            g2d.setColor(Color.WHITE);
            FontMetrics metrics = g2d.getFontMetrics(smallFont);
            String highScoreText = "HIGH SCORE";
            int textWidth = metrics.stringWidth(highScoreText);
            g2d.drawString(highScoreText, (SCREEN_WIDTH - textWidth) / 2, 20);

            String highScoreNum = String.valueOf(highScore);
            textWidth = metrics.stringWidth(highScoreNum);
            g2d.drawString(highScoreNum, (SCREEN_WIDTH - textWidth) / 2, 40);
            
            // Gọi các phương thức vẽ
            // showIntroScreen(g2d);
            if (image != null) {
                int x = (SCREEN_WIDTH - image.getWidth()) / 2 - 5;
                int y = (SCREEN_HEIGHT - image.getHeight()) / 2 - 100;
                g2d.drawImage(image, x, y, this);
            }
            drawScore(g2d);

            g2d.setFont(optionsFont);
            int optionY = SCREEN_HEIGHT / 2 + 50;
            for (int i = 0; i < options.length; i++) {
                int optionTextWidth = g2d.getFontMetrics(optionsFont).stringWidth(options[i]);
                int textX = (SCREEN_WIDTH - optionTextWidth) /2;

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

    

    private void drawScore(Graphics2D g) {
        String s = "Score: " + score;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        // Điều chỉnh tọa độ để hiển thị trong vùng nhìn thấy
        g.drawString(s, SCREEN_WIDTH / 2, SCREEN_HEIGHT - 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Testing::new);
    }
}