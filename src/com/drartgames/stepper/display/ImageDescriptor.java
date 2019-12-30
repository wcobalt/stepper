package com.drartgames.stepper.display;

public interface ImageDescriptor extends Positionable {
    float getWidth();

    Display getDisplay();

    Picture getPicture();

    float setWidth(float width);
}
