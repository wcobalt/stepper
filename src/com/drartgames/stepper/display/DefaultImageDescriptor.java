package com.drartgames.stepper.display;

import java.awt.image.BufferedImage;

public class DefaultImageDescriptor extends BaseFigureDescriptor implements ImageDescriptor {
    private Picture picture;

    public DefaultImageDescriptor(Display display, Picture picture, float width, float x, float y) {
        super(display, x, y, width, 0.0f);

        BufferedImage image = picture.getImage();
        super.setHeight(width * (image.getHeight() / (float)image.getWidth()));

        this.picture = picture;
    }

    @Override
    public void setHeight(float height) {}

    @Override
    public Picture getPicture() {
        return picture;
    }

    @Override
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return super.toString() + " IM: " + picture.getImage().getWidth() + "x" + picture.getImage().getHeight();
    }
}
