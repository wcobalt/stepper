package com.drartgames.stepper.display;

import java.util.ArrayList;
import java.util.List;

public class DefaultDisplayState implements DisplayState {
    private List<ImageDescriptor> images;
    private List<AudioDescriptor> audios;
    private List<AnimationDescriptor> animations;
    private List<TextDescriptor> texts;
    private List<InputDescriptor> inputs;
    private List<KeyAwaitDescriptor> keyAwaiters;
    private List<PostWorkDescriptor> works;

    private InputDescriptor activeInput;
    private TextDescriptor activeScrollableText;

    public DefaultDisplayState() {
        images = new ArrayList<>();
        audios = new ArrayList<>();
        animations = new ArrayList<>();
        texts = new ArrayList<>();
        inputs = new ArrayList<>();
        keyAwaiters = new ArrayList<>();
        works = new ArrayList<>();

        activeInput = null;
        activeScrollableText = null;
    }

    @Override
    public List<ImageDescriptor> getImageDescriptors() {
        return images;
    }

    @Override
    public List<TextDescriptor> getTextDescriptors() {
        return texts;
    }

    @Override
    public List<InputDescriptor> getInputDescriptors() {
        return inputs;
    }

    @Override
    public List<AnimationDescriptor> getAnimationDescriptors() {
        return animations;
    }

    @Override
    public List<KeyAwaitDescriptor> getKeyAwaitDescriptors() {
        return keyAwaiters;
    }

    @Override
    public List<AudioDescriptor> getAudioDescriptors() {
        return audios;
    }

    @Override
    public List<PostWorkDescriptor> getWorkDescriptors() {
        return works;
    }

    @Override
    public InputDescriptor getActiveInput() {
        return activeInput;
    }

    @Override
    public TextDescriptor getActiveScrollableText() {
        return activeScrollableText;
    }

    @Override
    public void setActiveInput(InputDescriptor descriptor) {
        activeInput = descriptor;
    }

    @Override
    public void setActiveScrollableText(TextDescriptor descriptor) {
        activeScrollableText = descriptor;
    }


}
