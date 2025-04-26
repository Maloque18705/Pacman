package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


import main.GamePanel;

public class KeyboardInputs implements KeyListener {

    public static List<Key> keyList = new ArrayList<>();
    public Key keyUp = new Key();
    public Key keyDown = new Key();
    public Key keyLeft = new Key();
    public Key keyRight = new Key();
    public Key keySpace = new Key();


    public KeyboardInputs(GamePanel g) {
        g.addKeyListener(this);
    }


    public void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keyLeft.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyRight.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keyUp.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keyDown.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keySpace.toggle(pressed);
        }
    }

    public class Key {
        public boolean isPressed;

        public Key() {
            keyList.add(this);
        }
        public void keyState() {
            System.out.println(isPressed);
        }
        public void toggle(boolean pressed) {
            if (pressed != isPressed) {
                isPressed = pressed;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }


    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}
