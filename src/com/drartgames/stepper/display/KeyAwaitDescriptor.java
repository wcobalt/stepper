package com.drartgames.stepper.display;

public interface KeyAwaitDescriptor extends Descriptor {
    void handle(int key);

    DescriptorWork getWork();

    int getKey();
}
