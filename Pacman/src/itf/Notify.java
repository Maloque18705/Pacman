package itf;

import entities.PacGum;
import entities.SuperPacGum;
import entities.ghosts.Ghosts;

public interface Notify {
   void registerObserver(Observer observer);
   void removeObserver(Observer observer);
   void notifyObserverPacGumEaten(PacGum pg);
   void notifyObserverSuperPacGumEaten(SuperPacGum spg);
   void notifyObserverGhostsCollision(Ghosts ghosts); 
}
