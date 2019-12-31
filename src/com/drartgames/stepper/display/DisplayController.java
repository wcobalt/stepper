package com.drartgames.stepper.display;

public interface DisplayController {
    Display getDisplay();

    void showMessage(String message, Picture picture);

    void showMessage(String message, Picture picture, int milliseconds);

    String showPrompt(String message, Picture picture);

    void setInputEnabled(boolean isEnabled);

    void setBackground(Picture picture);

    void setText(String message);
}