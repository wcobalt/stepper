package com.drartgames.stepper.display;

public class DefaultKeyAwaitDescriptor extends BaseDescriptor implements KeyAwaitDescriptor {
    private DescriptorWork keyAwaitWork;
    private int key;

    public DefaultKeyAwaitDescriptor(Display display, int key, DescriptorWork keyAwaitWork) {
        super(display);

        this.key = key;
        this.keyAwaitWork = keyAwaitWork;
    }

    @Override
    public void handle(int key) {
        if (this.key == key)
            keyAwaitWork.execute(this);
    }

    @Override
    public DescriptorWork getWork() {
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
