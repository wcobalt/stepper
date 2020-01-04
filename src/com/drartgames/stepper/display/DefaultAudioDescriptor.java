package com.drartgames.stepper.display;

public class DefaultAudioDescriptor extends BaseDescriptor implements AudioDescriptor {
    private Audio audio;
    private boolean isLooped;

    public DefaultAudioDescriptor(Display display, Audio audio, boolean isLooped) {
        super(display);

        this.audio = audio;
        this.isLooped = isLooped;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public boolean isLooped() {
        return isLooped;
    }

    @Override
    public void setIsLooped(boolean isLooped) {
        this.isLooped = isLooped;
    }

    @Override
    public String toString() {
        return "L: " + isLooped;
    }
}
