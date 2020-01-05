package com.drartgames.stepper.display;

public interface Descriptor {
    Display getDisplay();

    void setDoFree(boolean doFree);

    boolean doFree();
}
