package com.drartgames.stepper.display;

public class DefaultTextDescriptor extends BaseFigureDescriptor implements TextDescriptor {
    private String message;
    private int scrollPosition;
    private boolean wordWrap;

    public DefaultTextDescriptor(Display display, String message, float width, float height, float x, float y) {
        super(display, x, y, width, height);

        this.message = message;
        this.wordWrap = false;

        scrollPosition = 0;
    }

    @Override
    public String getMessage() {
        return message;
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
