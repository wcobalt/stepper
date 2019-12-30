package com.drartgames.stepper.display;

public interface AnimationDescriptor {
    ImageDescriptor getImageDescriptor();

    Animation getAnimation();

    boolean isLooped();

    void setIsLooped(boolean isLooped);

    Display getDisplay();
}
