package com.drartgames.stepper.display;

public class DefaultImageDescriptor implements ImageDescriptor {
    private float x, y, width, height;
    private Display display;
    private Picture picture;

    public DefaultImageDescriptor(Display display, Picture picture, float width, float x, float y) {
        this.display = display;
        this.picture = picture;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public Picture getPicture() {
        return picture;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float setWidth(float width) {
        return width;
    }
}
