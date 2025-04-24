package entities.ghosts;
import java.util.Objects;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import entities.MovingEntity;
import entities.ghosts.ghostState.*;
import entities.ghosts.ghostState.FrightMode;
import entities.ghosts.ghostState.GhostState;
import entities.ghosts.ghostState.ShedMode;
import entities.ghosts.ghostState.EatenMode;
import main.Game;

public abstract class Ghosts extends MovingEntity {
    protected GhostState state;
    protected boolean isChasing = false;
    
    protected int frightTimer = 0;
    protected int modeTimer = 0;
    protected int time;
    protected int count = 0;

    protected BufferedImage eatenSprite, frightSprite1, frightSprite2; 
    
    protected final GhostState chaseMode;
    protected final GhostState scatterMode;
    protected final GhostState frightMode;
    protected final GhostState spawnMode;
    protected final GhostState shedMode;
    protected final GhostState eatenMode;

   public Ghosts(int xPos, int yPos, String spriteName, int time) {
    super(xPos, yPos, 32, 2, spriteName, 2, 0.1f);
    chaseMode = new ChaseMode(this);
    scatterMode = new ScatterMode(this);
    frightMode = new FrightMode(this);
    this.spawnMode = null;
    shedMode = new ShedMode(this);
    eatenMode = new EatenMode(this);
    state = shedMode;
    this.time = time;


    try {
        eatenSprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("./res/img/ghost_eaten.png")));
        frightSprite1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("./res/img/ghost_frightened.png")));
        frightSprite2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("./res/img/ghost_frightened_2.png")));

    } catch (IOException e) {
        throw new RuntimeException();
    }
   }

   @Override 
   public void draw(Graphics2D g) {
    if (state == frightMode) {
        if (frightTimer <= (60*5) || (frightTimer % 20 > 10)) {
            g.drawImage(frightSprite1.getSubimage((int) subImg * size, 0, size, size), this.xPos, this.yPos, null);    
        } else {
            g.drawImage(frightSprite2.getSubimage((int) subImg * size, 0, size, size), this.xPos, this.yPos, null);    

        }

    } else if (state == eatenMode) {
        g.drawImage(eatenSprite.getSubimage(direction * size, 0, size, size),this.xPos, this.yPos, null);
    } else {
        g.drawImage(eatenSprite.getSubimage((int) subImg * size, 0, size, size),this.xPos, this.yPos, null);
    }
   }

   public abstract int[] getChaseTargetPosition();
   
   public abstract int[] getScatterTargetPosition();

   public void switchChaseMode() {
    state = chaseMode;
   }

   public void switchScatterMode() {
    state = scatterMode;
   }

   public void switchFrightMode() {
    frightTimer = 0;
    state = frightMode;
   }

   public void switchEatenMode() {
    state = eatenMode;
   }

   public void switchShedMode() {
    state = shedMode;
   }
    
   public void switchChaseModeOrScatterMode() {
    if (isChasing) {
        switchChaseMode();
    }
    else {
        switchScatterMode();
    }
   }

   @Override
   public void update() {
    if (count < (60 * time)) {
        count += 1;
    } else {
        if (!Game.getFirstInput()) return;

        if (state == frightMode) {
            frightTimer++;
            if (frightTimer >= (60*10)) {
                state.timeFrightModeOver();
                frightTimer = 0;
            }
        }

        if (state == chaseMode || state == scatterMode) {
            modeTimer++;
            if ((isChasing && modeTimer >= (60*20)) || (!isChasing && modeTimer >= (60*5))) {
                state.timerModeOver();
                isChasing = !isChasing;
                modeTimer = 0;
            }
        }

        if (xPos == 208 && yPos == 168) {
            state.outShed();
        }

        
        if (xPos == 208 && yPos == 200) {
            state.inShed();
        }

        state.computerNextDir();
        updatePos();
    }

   }

   public void setState(GhostState state) {
    this.state = state;
   }

   @Override
   public String toString() {
    return state.toString();
   }

   public GhostState getState() {
    return state;
   }

   @Override 
   public void reset() {
    super.reset();
    state = shedMode;
    isChasing = false;
   }
}


