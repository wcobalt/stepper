package com.drartgames.stepper.display;

public interface Animation {
    //true if animation's ended else - otherwise
    boolean update(ImageDescriptor imageDescriptor, long milliseconds);
}
