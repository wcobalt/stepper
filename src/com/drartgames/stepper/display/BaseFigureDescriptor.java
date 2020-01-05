package com.drartgames.stepper.display;

public class BaseFigureDescriptor extends BaseDescriptor implements FigureDescriptor {
    private float width, height, x, y;
    private boolean isVisible;

    public BaseFigureDescriptor(Display display, float x, float y, float width, float height) {
        super(display);

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        isVisible = true;
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
    public float getHeight() {
        return height;
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
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "W: " + width + " H: " + height + " X: " + x + " Y: " + y;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
