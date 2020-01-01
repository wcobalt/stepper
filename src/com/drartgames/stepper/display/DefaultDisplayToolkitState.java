package com.drartgames.stepper.display;

public class DefaultDisplayToolkitState implements DisplayToolkitState {
    private ImageDescriptor backgroundImage;
    private TextDescriptor mainText;
    private InputDescriptor mainInput;
    private InputWork inputWork;

    public DefaultDisplayToolkitState(ImageDescriptor backgroundImage, TextDescriptor mainText, InputDescriptor mainInput,
                                      InputWork inputWork) {
        this.backgroundImage = backgroundImage;
        this.mainText = mainText;
        this.mainInput = mainInput;
        this.inputWork = inputWork;
    }

    public ImageDescriptor getBackgroundDescriptor() {
        return backgroundImage;
    }

    public TextDescriptor getMainTextDescriptor() {
        return mainText;
    }

    public InputDescriptor getMainInputDescriptor() {
        return mainInput;
    }

    @Override
    public InputWork getInputWork() {
        return inputWork;
    }
}
