package com.drartgames.stepper.display;

public class DefaultAnimationDescriptor extends BaseDescriptor implements AnimationDescriptor {
    private ImageDescriptor imageDescriptor;
    private Animation animation;
    private boolean isLooped, doReturnBack;

    public DefaultAnimationDescriptor(Display display, ImageDescriptor imageDescriptor, Animation animation,
                                      boolean isLooped, boolean doReturnBack) {
        super(display);

        this.imageDescriptor = imageDescriptor;
        this.animation = animation;
        this.isLooped = isLooped;
        this.doReturnBack = doReturnBack;
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
    public boolean doReturnBack() {
        return doReturnBack;
    }

    @Override
    public String toString() {
        return "L: " + isLooped + " R: " + doReturnBack + " I:\n   " + imageDescriptor.toString();
    }
}
