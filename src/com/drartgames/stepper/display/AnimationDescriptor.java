package com.drartgames.stepper.display;

public interface AnimationDescriptor extends Descriptor {
    ImageDescriptor getImageDescriptor();

    Animation getAnimation();

    boolean isLooped();

    void setIsLooped(boolean isLooped);

    boolean doReturnBack();
}
