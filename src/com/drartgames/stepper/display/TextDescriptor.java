package com.drartgames.stepper.display;

import javax.swing.*;

public interface TextDescriptor extends Positionable, Scalable {
    Display getDisplay();

    String getMessage();

    int getScrollPosition();

    void setScrollPosition(int scrollPosition);

    void setMessage(String message);
}
