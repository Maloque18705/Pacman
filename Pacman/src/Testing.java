import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.Objects;

public class Testing extends JFrame {
        JButton startButton = new JButton();
        startButton.setBounds(200, 100, 100, 50);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
        this.add(startButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Testing::new);
    }
}
