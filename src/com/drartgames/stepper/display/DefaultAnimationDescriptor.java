package com.drartgames.stepper.display;

public class DefaultAnimationDescriptor implements AnimationDescriptor {
    private Display display;
    private ImageDescriptor imageDescriptor;
    private Animation animation;
    private boolean isLooped;

    public DefaultAnimationDescriptor(Display display, ImageDescriptor imageDescriptor, Animation animation, boolean isLooped) {
        this.display = display;
        this.imageDescriptor = imageDescriptor;
        this.animation = animation;
        this.isLooped = isLooped;
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
    public Display getDisplay() {
        return display;
    }
}
