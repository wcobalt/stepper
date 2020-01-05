package com.drartgames.stepper.display.animations;

import com.drartgames.stepper.display.ImageDescriptor;

public class MotionToPointAnimation extends BaseAnimation {
    private float stepX, stepY;
    private int duration;
    private float finalX, finalY;

    private static final float ERROR = 0.005f;

    public MotionToPointAnimation(int duration, float finalX, float finalY) {
        this.duration = duration;
        this.finalX = finalX;
        this.finalY = finalY;
    }

    @Override
    public boolean update(ImageDescriptor imageDescriptor, long milliseconds) {
        float x = imageDescriptor.getX(), y = imageDescriptor.getY();

        imageDescriptor.setX(x + stepX * milliseconds);
        imageDescriptor.setY(y + stepY * milliseconds);

        return (Math.abs(x - finalX) <= ERROR && Math.abs(y - finalY) <= ERROR);
    }

    @Override
    public void setInitPos(ImageDescriptor imageDescriptor) {
        super.setInitPos(imageDescriptor);

        stepX = (finalX - initX) / duration;
        stepY = (finalY - initY) / duration;
    }

    @Override
    public void resetStep() {}

    @Override
    public void loopEnded() {}
}
