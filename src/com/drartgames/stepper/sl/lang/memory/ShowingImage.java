package com.drartgames.stepper.sl.lang.memory;

import com.drartgames.stepper.display.AnimationDescriptor;
import com.drartgames.stepper.display.ImageDescriptor;

public interface ShowingImage extends Entity {
    ImageDescriptor getImage();

    void setCurrentAnimation(AnimationDescriptor animation);

    AnimationDescriptor getCurrentAnimation();
}
