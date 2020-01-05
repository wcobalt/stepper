package com.drartgames.stepper.display;

public class DefaultDisplayToolkitState implements DisplayToolkitState {
    private ImageDescriptor backgroundImage;
    private TextDescriptor mainText;
    private InputDescriptor mainInput;
    private InputWork inputWork;
    private KeyAwaitDescriptor keyAwaitDescriptor;

    public DefaultDisplayToolkitState(ImageDescriptor backgroundImage, TextDescriptor mainText, InputDescriptor mainInput,
                                      KeyAwaitDescriptor keyAwaitDescriptor, InputWork inputWork) {
        this.backgroundImage = backgroundImage;
        this.mainText = mainText;
        this.mainInput = mainInput;
        this.keyAwaitDescriptor = keyAwaitDescriptor;
        this.inputWork = inputWork;
    }

    @Override
    public ImageDescriptor getBackgroundDescriptor() {
        return backgroundImage;
    }

    @Override
    public TextDescriptor getMainTextDescriptor() {
        return mainText;
    }

    @Override
    public InputDescriptor getMainInputDescriptor() {
        return mainInput;
    }

    @Override
    public KeyAwaitDescriptor getKeyAwaitDescriptor() {
        return keyAwaitDescriptor;
    }

    @Override
    public InputWork getInputWork() {
        return inputWork;
    }
}
