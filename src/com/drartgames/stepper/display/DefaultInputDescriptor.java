package com.drartgames.stepper.display;

public class DefaultInputDescriptor implements InputDescriptor {
    private Display display;
    private float width, height, x, y;
    private String currentText;

    public DefaultInputDescriptor(Display display, float width, float height, float x, float y) {
        this.display = display;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        currentText = "";
    }

    @Override
    public String getCurrentText() {
        return currentText;
    }

    @Override
    public void setCurrentText(String text) {
        this.currentText = text;
    }

    @Override
    public Display getDisplay() {
        return display;
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
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
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
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
