package utils;

import java.awt.Rectangle;

import entities.Entity;
import entities.GhostShed;
import entities.Wall;
import main.Game;

public class CheckCollision {
   public static boolean checkWallCollision(Entity obj, int dx, int dy) {
    Rectangle rect = new Rectangle(obj.getXPos() + dx, obj.getYPos() + dy, obj.getSize(), obj.getSize());
    for (Wall w : Game.getWalls()) {
        if(w.getRect().intersects(rect)) {
            return true;
        }
    }
    return false;
   }
   
   public static boolean checkWallCollisionIgnoreGhostShed(Entity obj, int dx, int dy) {
    Rectangle rect = new Rectangle(obj.getXPos() + dx, obj.getYPos() + dy, obj.getSize(), obj.getSize());
    for( Wall w : Game.getWalls()) {
        if (!(w instanceof GhostShed) && w.getRect().intersects(rect)) {
            return true;
        }
    }
    return false;
   }

   public static Entity checkCollision(Entity obj, Class<?extends Entity> collisionCheck) {
    for (Entity e : Game.getEntities()) {
        if(!e.isDestroyed() && collisionCheck.isInstance(e) && e.getRect().contains(obj.getXPos() + obj.getSize()/2, obj.getYPos() + obj.getSize()/2))
            return e;
    }
    return null;
   }


}
