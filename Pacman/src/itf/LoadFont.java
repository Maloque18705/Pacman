package itf;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class LoadFont {
    public static Font loadFont() {
        try {
            InputStream fontStream = LoadFont.class.getClassLoader().getResourceAsStream("res/font/Emulogic-zrEw.ttf");
            if (fontStream == null) {
                throw new IOException("Không tìm thấy font");
            }
            return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(10f);
        } catch (IOException | FontFormatException e) {
            System.err.println("Failed to load font: res/font/Emulogic-zrEw.ttf");
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 10);
        }
    }
}