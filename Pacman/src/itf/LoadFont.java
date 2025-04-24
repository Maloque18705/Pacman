package itf;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class LoadFont {
    private static Font customFont;

   public static Font loadFont() {
    
    try {
        InputStream fontStream = LoadFont.class.getClassLoader().getResourceAsStream("res/font/Emulogic-zrEw.ttf");
        if (fontStream == null) {
            throw new IOException("Không tìm thấy font");
        }
        customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(.30f);
    } catch (IOException | FontFormatException e) {
       e.printStackTrace(); 
       customFont = new Font("San Serif", Font.PLAIN, 10);
    }
    return customFont;
   } 
}
