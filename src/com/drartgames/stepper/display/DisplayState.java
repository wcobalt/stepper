package com.drartgames.stepper.display;

import java.util.List;

public interface DisplayState {
    List<ImageDescriptor> getImageDescriptors();

    List<TextDescriptor> getTextDescriptors();

    List<InputDescriptor> getInputDescriptors();

    List<AnimationDescriptor> getAnimationDescriptors();

    List<KeyAwaitDescriptor> getKeyAwaitDescriptors();

    List<AudioDescriptor> getAudioDescriptors();

    List<PostWorkDescriptor> getWorkDescriptors();

    InputDescriptor getActiveInput();

    TextDescriptor getActiveScrollableText();

    void setActiveInput(InputDescriptor descriptor);

    void setActiveScrollableText(TextDescriptor descriptor);
}
