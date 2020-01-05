package com.drartgames.stepper.display;

public interface PostWorkDescriptor extends Descriptor {
    void execute();

    DescriptorWork getWork();
}
