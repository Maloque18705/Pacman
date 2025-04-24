import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.Objects;

public class Testing extends JFrame {
    private BufferedImage sprite;
    private final String spriteName = "blinky.png";

    public Testing() {
        setTitle("Test Load MovingEntity Image");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // —— thử load như MovingEntity ——
        try {
            String path = "res/img/" + spriteName;
            URL url = getClass().getClassLoader().getResource(path);
            System.out.println("Trying to load resource at: " + path);
            System.out.println("Resolved URL: " + url);
            sprite = ImageIO.read(Objects.requireNonNull(url));
            System.out.println("Loaded sprite size: " + sprite.getWidth() + "×" + sprite.getHeight());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            sprite = null;
        }

        // đặt panel vẽ
        add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (sprite != null) {
                    // vẽ ở giữa panel
                    int x = (getWidth() - sprite.getWidth()) / 2;
                    int y = (getHeight() - sprite.getHeight()) / 2;
                    g.drawImage(sprite, x, y, this);
                } else {
                    g.setColor(Color.RED);
                    g.drawString("Load sprite failed!", 20, 20);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Testing::new);
    }
}
