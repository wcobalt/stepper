package com.drartgames.stepper.display;

public interface InputDescriptor extends Positionable, Scalable {
    String getCurrentText();

    void setCurrentText(String text);
}
