package itf;

import entities.ghosts.Ghosts;
import entities.PacGum;
import entities.SuperPacGum;

public interface Observer {
    void updatePacGumEaten(PacGum pg);
    void updateSuperPacGumEaten(SuperPacGum spg);
    void updateGhostCollision(Ghosts gh);    
}
