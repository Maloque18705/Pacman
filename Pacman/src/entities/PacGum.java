package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class PacGum extends StaticEntity{
   public PacGum(int xPos, int yPos) {
    super(xPos + 8, yPos + 8, 4);
   }
   
   @Override
   public void draw(Graphics2D g) {
    g.setColor(new Color(255, 183, 174));
    g.fillRect(xPos, yPos, size, size);;
   }
}
