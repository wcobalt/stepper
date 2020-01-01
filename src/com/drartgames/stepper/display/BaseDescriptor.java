package com.drartgames.stepper.display;

public class BaseDescriptor implements Descriptor {
    private boolean doFree;
    private Display display;

    public BaseDescriptor(Display display) {
        this.display = display;
    }

    @Override
    public void setDoFree(boolean doFree) {
        this.doFree = doFree;
    }

    @Override
    public boolean doFree() {
        return doFree;
    }

    @Override
    public Display getDisplay() {
        return display;
    }
}
