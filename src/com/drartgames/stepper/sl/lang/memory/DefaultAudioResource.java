package com.drartgames.stepper.sl.lang.memory;

import com.drartgames.stepper.display.Audio;

public class DefaultAudioResource extends BaseEntity implements AudioResource {
    private Audio audio;

    public DefaultAudioResource(String name, Audio audio) {
        super(name);
        this.audio = audio;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }
}
