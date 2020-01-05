package com.drartgames.stepper.sl.lang.memory;

import com.drartgames.stepper.display.AnimationDescriptor;
import com.drartgames.stepper.display.ImageDescriptor;

public class DefaultShowingImage extends BaseEntity implements ShowingImage {
    private ImageDescriptor imageDescriptor;
    private AnimationDescriptor animationDescriptor;

    public DefaultShowingImage(String name, ImageDescriptor imageDescriptor, AnimationDescriptor animationDescriptor) {
        super(name);
        this.imageDescriptor = imageDescriptor;
        this.animationDescriptor = animationDescriptor;
    }

    @Override
    public void setCurrentAnimation(AnimationDescriptor animation) {
        this.animationDescriptor = animation;
    }

    @Override
    public ImageDescriptor getImage() {
        return imageDescriptor;
    }

    @Override
    public AnimationDescriptor getCurrentAnimation() {
        return animationDescriptor;
    }
}
