package com.drartgames.stepper.display;

public interface DisplayToolkit {
    Display getDisplay();

    void initialize(InputWork inputWork);

    void setState(DisplayToolkitState state);

    DisplayToolkitState getState();

    DisplayToolkitState makeNewState();

    void showMessage(String message, Picture picture);

    void showPrompt(String message, Picture picture, InputWork inputWork);

    void setBackground(Picture picture);

    void setText(String message);
}