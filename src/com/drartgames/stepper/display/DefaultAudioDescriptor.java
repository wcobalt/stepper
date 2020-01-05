package com.drartgames.stepper.display;

import javax.sound.sampled.Clip;

public class DefaultAudioDescriptor extends BaseDescriptor implements AudioDescriptor {
    private static final long NO_CONTINUE_FROM = -1;
    private Audio audio;
    private boolean isLooped, isPlaying, muteForStateSwap, isPaused, wasStarted;
    private long continueFrom;
    private DescriptorWork audioEndWork;

    public DefaultAudioDescriptor(Display display, Audio audio, boolean isLooped, DescriptorWork audioEndWork) {
        super(display);

        this.audio = audio;
        this.isLooped = isLooped;
        this.audioEndWork = audioEndWork;

        isPaused = false;
        isPlaying = false;
        continueFrom = NO_CONTINUE_FROM;
        muteForStateSwap = false;
        wasStarted = false;
    }

    @Override
    public void update() {
        if (isPlaying && !audio.getClip().isRunning())
            isPlaying = false;

        if (!isPlaying && !muteForStateSwap && !isPaused && wasStarted) {
            if (audioEndWork != null)
                audioEndWork.execute(this);
            setDoFree(true);

        }
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
    public void stop() {
        if (isPlaying) {
            isPlaying = false;
            isPaused = true;

            audio.getClip().stop();
            continueFrom = audio.getClip().getMicrosecondPosition();
        }
    }

    @Override
    public void start() {
        if (!isPlaying) {
            if (isPaused)
                isPaused = false;

            long from = continueFrom != NO_CONTINUE_FROM ? continueFrom : 0;

            audio.getClip().setMicrosecondPosition(from);
            handleLooping();
            audio.getClip().start();

            isPlaying = true;
            wasStarted = true;
        }
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public String toString() {
        return "L: " + isLooped + " P: " + isPaused + " PL: " + isPlaying + " M: " + muteForStateSwap + " S: " +
                wasStarted + " CF: " + continueFrom;
    }

    @Override
    public void setMuteForStateSwap(boolean mute) {
        this.muteForStateSwap = mute;
    }

    @Override
    public boolean isMutedForStateSwap() {
        return muteForStateSwap;
    }

    private void handleLooping() {
        if (!isPlaying) {
            if (isLooped)
                audio.getClip().loop(Clip.LOOP_CONTINUOUSLY);
            else
                audio.getClip().loop(0);
        }
    }
}
