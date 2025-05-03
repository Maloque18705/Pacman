package utils;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Sound {
    private Clip clip;
    private AudioInputStream sound;
    
    public Sound(String name) {
        try {
            try {
                sound = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("res/sound/" + name).toURI().toURL());
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (LineUnavailableException | URISyntaxException e) {
                throw new RuntimeException();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if (clip != null) {
            try {
                if (clip.isRunning()) {
                    clip.stop(); 
                }
                clip.setMicrosecondPosition(0); 
                clip.start(); 
            } catch (Exception e) {
                System.err.println("Failed to play sound: " + e.getMessage());
            }
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
        }
    }

    public void close() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        if (sound != null) {
            try {
                sound.close();
            } catch (IOException e) {
                System.err.println("Failed to close sound: " + e.getMessage());
            }
        }
    }
}
