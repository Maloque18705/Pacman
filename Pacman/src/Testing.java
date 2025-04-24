import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Testing extends JFrame {

    private BufferedImage image;
    private Font customFont;

    public Testing() {
        setTitle("Font & Image Test");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // căn giữa cửa sổ

        try {

            // ✅ Tải font từ file .ttf
            System.out.println("Font absolute path: " + new File("res/font/Emulogic-zrEw.ttf").getAbsolutePath());
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("res/font/Emulogic-zrEw.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

            // Đăng ký font với hệ thống đồ họa
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Vẽ ảnh
        if (image != null) {
            g.drawImage(image, 50, 50, this);
        }

        // Vẽ văn bản với font
        if (customFont != null) {
            g.setFont(customFont);
            g.setColor(Color.YELLOW);
            g.drawString("Hello Pac-Man!", 50, 350);
        } else {
            g.drawString("Không load được font!", 50, 350);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Testing());
    }
}
