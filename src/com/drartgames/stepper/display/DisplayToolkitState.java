package com.drartgames.stepper.display;

public interface DisplayToolkitState {
    TextDescriptor getMainTextDescriptor();

    InputDescriptor getMainInputDescriptor();

    ImageDescriptor getBackgroundDescriptor();

    ImageDescriptor getBackTextDescriptor();

    KeyAwaitDescriptor getKeyAwaitDescriptor();

    InputWork getInputWork();
}
