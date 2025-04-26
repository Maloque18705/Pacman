package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class SuperPacPellet extends StaticEntity {
   private int blinkTimer;
   
   public SuperPacPellet(int xPos, int yPos) {
    super(xPos, yPos, 16);
    blinkTimer = 0;
   }

   @Override
   public void update() {
    blinkTimer++;
    if (blinkTimer > 60) blinkTimer = 0;
   }

   @Override
   public void draw(Graphics2D g) {
    if (blinkTimer < 30) {
        g.setColor(new Color(255, 183, 174));
        g.fillOval(xPos, yPos, size, size);
    }
   }
}
