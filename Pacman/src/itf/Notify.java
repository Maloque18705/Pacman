package itf;

import entities.PacPellet;
import entities.SuperPacPellet;
import entities.ghosts.Ghosts;

public interface Notify {
   void registerObserver(Observer observer);
   void removeObserver(Observer observer);
   void notifyObserverPacPelletEaten(PacPellet pl);
   void notifyObserverSuperPacPelletEaten(SuperPacPellet spl);
   void notifyObserverGhostsCollision(Ghosts ghosts); 
}
