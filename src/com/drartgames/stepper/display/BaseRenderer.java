package com.drartgames.stepper.display;

public class BaseRenderer implements Renderer {
    private Display display;

    @Override
    public void setDisplay(Display display) {
        this.display = display;
    }

    @Override
    public Display getDisplay() {
        return display;
    }
}
