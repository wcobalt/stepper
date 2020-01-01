package com.drartgames.stepper.display;

public interface KeyAwaitDescriptor extends Descriptor {
    KeyAwaitWork getWork();

    int getKey();
}
