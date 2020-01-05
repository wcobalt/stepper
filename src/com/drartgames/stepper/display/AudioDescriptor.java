package com.drartgames.stepper.display;

public interface AudioDescriptor extends Descriptor {
    void update();

    Audio getAudio();

    boolean isLooped();

    void setIsLooped(boolean isLooped);

    void stop();

    void start();

    boolean isPlaying();

    void setMuteForStateSwap(boolean mute);

    boolean isMutedForStateSwap();
}
