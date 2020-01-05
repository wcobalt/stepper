package com.drartgames.stepper.display;

public interface AnimationDescriptor extends Descriptor {
    void update(long milliseconds);

    ImageDescriptor getImageDescriptor();

    Animation getAnimation();

    boolean isLooped();

    void setIsLooped(boolean isLooped);
}
