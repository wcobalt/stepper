package com.drartgames.stepper.display;

public class DefaultAnimationDescriptor extends BaseDescriptor implements AnimationDescriptor {
    private ImageDescriptor imageDescriptor;
    private Animation animation;
    private boolean isLooped;
    private DescriptorWork animationEndWork;

    public DefaultAnimationDescriptor(Display display, ImageDescriptor imageDescriptor, Animation animation,
                                      boolean isLooped, DescriptorWork animationEndWork) {
        super(display);

        this.imageDescriptor = imageDescriptor;
        this.animation = animation;
        this.isLooped = isLooped;
        this.animationEndWork = animationEndWork;
    }

    @Override
    public void update(long milliseconds) {
        boolean result = animation.update(imageDescriptor, milliseconds);

        if (result && animationEndWork != null) {
            if (isLooped) {
                animation.loopEnded();
            } else {
                animationEndWork.execute(this);

                setDoFree(true);
            }
        }
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return imageDescriptor;
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public boolean isLooped() {
        return isLooped;
    }

    @Override
    public void setIsLooped(boolean isLooped) {
        this.isLooped = isLooped;
    }

    @Override
    public String toString() {
        return "L: " + isLooped + " I:\n   " + imageDescriptor.toString();
    }
}
