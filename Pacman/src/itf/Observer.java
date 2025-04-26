package itf;

import entities.ghosts.Ghosts;
import entities.PacPellet;
import entities.SuperPacPellet;

public interface Observer {
    void updatePacPelletEaten(PacPellet pl);
    void updateSuperPacPelletEaten(SuperPacPellet spl);
    void updateGhostCollision(Ghosts gh);    
}
