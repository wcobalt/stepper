package com.drartgames.stepper.display;

public class DefaultKeyAwaitDescriptor extends BaseDescriptor implements KeyAwaitDescriptor {
    private KeyAwaitWork keyAwaitWork;
    private int key;

    public DefaultKeyAwaitDescriptor(Display display, int key, KeyAwaitWork keyAwaitWork) {
        super(display);

        this.key = key;
        this.keyAwaitWork = keyAwaitWork;
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
    public String toString() {
        return "K: " + key;
    }
}
