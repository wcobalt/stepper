package com.drartgames.stepper.display;

public interface KeyAwaitDescriptor {
    KeyAwaitWork getWork();

    int getKey();

    boolean doFree();

    void setDoFree(boolean doFree);
}
