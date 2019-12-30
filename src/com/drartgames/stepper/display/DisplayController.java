package com.drartgames.stepper.display;

public interface DisplayController {
    void showLoading(String message);

    void hideLoading();

    Display getDisplay();

    void showMessage(String message, Picture picture);

    void showMessage(String message, Picture picture, int milliseconds);

    String showPrompt(String message, Picture picture);

    void setInputEnabled(boolean isEnabled);

    void setBackground(Picture picture);

    void setText(String message);
}