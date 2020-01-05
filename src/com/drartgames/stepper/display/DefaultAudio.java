package com.drartgames.stepper.display;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultAudio implements Audio {
    private static final Logger logger = Logger.getLogger(DefaultAudio.class.getName());
    private Clip clip;

    public DefaultAudio(String file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(file));

        try {
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (LineUnavailableException exc) {
            logger.log(Level.SEVERE, "Line unavailable exception", exc);
        }
    }

    public DefaultAudio(Clip clip) {
        this.clip = clip;
    }

    @Override
    public Clip getClip() {
        return clip;
    }
}
