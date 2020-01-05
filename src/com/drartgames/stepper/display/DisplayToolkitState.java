package com.drartgames.stepper.display;

public interface DisplayToolkitState {
    TextDescriptor getMainTextDescriptor();

    InputDescriptor getMainInputDescriptor();

    ImageDescriptor getBackgroundDescriptor();

    KeyAwaitDescriptor getKeyAwaitDescriptor();

    InputWork getInputWork();
}
