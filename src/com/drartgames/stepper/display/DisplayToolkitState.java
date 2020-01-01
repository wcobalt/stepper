package com.drartgames.stepper.display;

public interface DisplayToolkitState {
    TextDescriptor getMainTextDescriptor();

    InputDescriptor getMainInputDescriptor();

    ImageDescriptor getBackgroundDescriptor();

    InputWork getInputWork();
}
