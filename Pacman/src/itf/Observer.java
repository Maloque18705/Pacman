package itf;

import entities.PacPellet;
import entities.SuperPacPellet;
import entities.ghosts.Ghosts;

public interface Observer {
    void updatePacPelletEaten(PacPellet pl);
    void updateSuperPacPelletEaten(SuperPacPellet spl);
    void updateGhostCollision(Ghosts ghosts);
}