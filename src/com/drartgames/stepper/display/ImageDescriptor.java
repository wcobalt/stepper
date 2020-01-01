package com.drartgames.stepper.display;

public interface ImageDescriptor extends Positionable, Scalable {
    Picture getPicture();

    void setPicture(Picture picture);
}
