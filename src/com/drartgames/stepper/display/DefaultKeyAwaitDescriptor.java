package com.drartgames.stepper.display;

public class DefaultKeyAwaitDescriptor implements KeyAwaitDescriptor {
    private KeyAwaitWork keyAwaitWork;
    private int key;
    private boolean doFree;

    public DefaultKeyAwaitDescriptor(int key, KeyAwaitWork keyAwaitWork) {
        this.key = key;
        this.keyAwaitWork = keyAwaitWork;

        this.doFree = false;
    }

    @Override
    public KeyAwaitWork getWork() {
        return keyAwaitWork;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public boolean doFree() {
        return doFree;
    }

    @Override
    public void setDoFree(boolean doFree) {
        this.doFree = doFree;
    }
}
