package com.drartgames.stepper.display;

public interface AudioDescriptor extends Descriptor {
    Audio getAudio();

    boolean isLooped();

    void setIsLooped(boolean isLooped);
}
