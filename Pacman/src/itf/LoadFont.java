package itf;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class LoadFont {
   public static Font loadFont() {
    InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("font.ttf");
    Font font = null;
    try {
        assert stream != null:
        font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(10f);
    } catch (FontFormatException | IOException e) {
        throw new RuntimeException();
    }
    return font;
   } 
}
