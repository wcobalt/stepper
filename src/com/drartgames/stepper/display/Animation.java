package com.drartgames.stepper.display;

public interface Animation {
    //true if animation's ended else - otherwise
    boolean update(ImageDescriptor imageDescriptor, long milliseconds);

    //to reset initPos
    void setInitPos(ImageDescriptor imageDescriptor);

    //to set pos as initPos
    void backToInitPos(ImageDescriptor imageDescriptor);

    //to set frame to zero
    void resetStep();

    //call when a loop is ended
    void loopEnded();
}
