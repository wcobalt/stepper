package com.drartgames.stepper.display;

public class DefaultAudioDescriptor implements AudioDescriptor {
    private Audio audio;
    private Display display;
    private boolean isLooped;

    public DefaultAudioDescriptor(Display display, Audio audio, boolean isLooped) {
        this.display = display;
        this.audio = audio;
        this.isLooped = isLooped;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public boolean isLooped() {
        return isLooped;
    }

    @Override
    public void setIsLooped(boolean isLooped) {
        this.isLooped = isLooped;
    }
}
