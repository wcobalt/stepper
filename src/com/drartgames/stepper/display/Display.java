package com.drartgames.stepper.display;

import com.drartgames.stepper.initializer.Initializer;

import java.awt.*;

public interface Display {
    ImageDescriptor addPicture(Picture picture, float width, float x, float y);

    AudioDescriptor addAudio(Audio audio, boolean isLooped, DescriptorWork audioEndWork);

    AnimationDescriptor addAnimation(ImageDescriptor imageDescriptor, Animation animation, boolean isLooped, DescriptorWork animationEndWork);

    TextDescriptor addText(String message, float width, float height, float x, float y);

    InputDescriptor addInput(float width, float height, float x, float y);

    KeyAwaitDescriptor awaitForKey(int key, DescriptorWork keyAwaitWork);

    PostWorkDescriptor addWork(DescriptorWork work);

    void run();

    void stop();

    void initialize();

    Initializer getInitializer();

    Dimension getRenderResolution();

    DisplayState getDisplayState();

    void provideDisplayState(DisplayState displayState, Work changeWork);

    TextRenderer getTextRenderer();
}
