package com.drartgames.stepper.display;

import com.drartgames.stepper.initializer.Initializer;

import java.awt.*;

public interface Display {
    ImageDescriptor addPicture(Picture picture, float width, float x, float y);

    AudioDescriptor addAudio(Audio audio, boolean isLooped);

    AnimationDescriptor addAnimation(ImageDescriptor imageDescriptor, Animation animation, boolean isLooped, boolean doReturnBack);

    TextDescriptor addText(String message, float width, float height, float x, float y);

    InputDescriptor addInput(float width, float height, float x, float y);

    void setScrollableText(TextDescriptor textDescriptor);

    void setActiveInput(InputDescriptor inputDescriptor);

    KeyAwaitDescriptor awaitForKey(int key, KeyAwaitWork keyAwaitWork);

    TextDescriptor getScrollableText();

    InputDescriptor getActiveInput();

    void run();

    void stop();

    void initialize();

    Initializer getInitializer();

    Dimension getRenderResolution();
}
