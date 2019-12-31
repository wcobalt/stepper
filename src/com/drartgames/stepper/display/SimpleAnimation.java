package com.drartgames.stepper.display;

public class SimpleAnimation implements Animation {
    private static final int FORWARD_DIRECTION = 1;
    private static final int REVERSE_DIRECTION = -1;

    private int duration;
    private int currentTime;
    private int modifier = FORWARD_DIRECTION;
    private float initX, initY;
    private float amplitude;
    private boolean isVertical;

    public SimpleAnimation(int duration, float amplitude, boolean isVertical) {
        this.duration = duration;
        this.amplitude = amplitude;
        this.isVertical = isVertical;

        resetStep();
    }

    @Override
    public boolean update(ImageDescriptor imageDescriptor, long milliseconds) {
        currentTime += milliseconds;

        if (currentTime > duration) currentTime = duration;

        //current animation stage 0.0-1.0
        double step = currentTime / (double)duration;
        double offset = -amplitude + (3 * Math.pow(step, 2) - 2 * Math.pow(step, 3)) * 2 * amplitude;

        float finalOffset = (float)offset * modifier;

        if (isVertical)
            imageDescriptor.setY(initY + finalOffset);
        else
            imageDescriptor.setX(initX + finalOffset);

        return currentTime == duration;
    }

    @Override
    public void resetStep() {
        currentTime = 0;
        modifier = FORWARD_DIRECTION;
    }

    @Override
    public void loopEnded() {
        modifier = modifier == FORWARD_DIRECTION ? REVERSE_DIRECTION : FORWARD_DIRECTION;
        currentTime = 0;
    }

    @Override
    public void setInitPos(ImageDescriptor imageDescriptor) {
        initX = imageDescriptor.getX();
        initY = imageDescriptor.getY();
    }

    @Override
    public void backToInitPos(ImageDescriptor imageDescriptor) {
        imageDescriptor.setX(initX);
        imageDescriptor.setY(initY);
    }
}
