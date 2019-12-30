package com.drartgames.stepper.display;

public interface AudioDescriptor {
    Audio getAudio();

    Display getDisplay();

    boolean isLooped();

    void setIsLooped(boolean isLooped);
}
