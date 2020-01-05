package com.drartgames.stepper.display.animations;

import com.drartgames.stepper.display.Animation;
import com.drartgames.stepper.display.ImageDescriptor;

public abstract class BaseAnimation implements Animation {
    protected float initX, initY;

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
