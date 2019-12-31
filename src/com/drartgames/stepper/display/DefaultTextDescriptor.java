package com.drartgames.stepper.display;

public class DefaultTextDescriptor implements TextDescriptor {
    private String message;
    private float width, height, x, y;
    private Display display;
    private int scrollPosition;
    private boolean wordWrap;

    public DefaultTextDescriptor(Display display, String message, float width, float height, float x, float y) {
        this.display = display;
        this.message = message;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.wordWrap = false;

        scrollPosition = 0;
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
    public Display getDisplay() {
        return display;
    }

    @Override
    public String getMessage() {
        return message;
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
    public int getScrollPosition() {
        return scrollPosition;
    }

    @Override
    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setWordWrap(boolean wordWrap) {
        this.wordWrap = wordWrap;
    }

    @Override
    public boolean isWordWrap() {
        return wordWrap;
    }
}
