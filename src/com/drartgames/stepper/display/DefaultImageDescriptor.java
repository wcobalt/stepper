package com.drartgames.stepper.display;

public class DefaultImageDescriptor extends BaseFigureDescriptor implements ImageDescriptor {
    private Picture picture;

    public DefaultImageDescriptor(Display display, Picture picture, float width, float x, float y) {
        super(display, x, y, width, 0.0f);

        this.picture = picture;
    }

    @Override
    public Picture getPicture() {
        return picture;
    }

    @Override
    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
