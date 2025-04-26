package entities.ghosts.ghostState;

import entities.ghosts.Ghosts;
public abstract class GhostState {
   protected Ghosts ghosts;
   
   public GhostState(Ghosts ghosts) {
    this.ghosts = ghosts;
   }


   public void superPacPelletEaten() {}

   public void timerModeOver() {}

   public void timeFrightModeOver() {}

   public void eaten() {} 
   
   public void outShed() {}

   public void inShed() {}

   public int[] getTargetPosition() {
    return new int[2];
   }

   public abstract void computerNextDir();
   
}
