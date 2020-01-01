package com.drartgames.stepper.display;

import javax.swing.*;

public interface TextDescriptor extends Positionable, Scalable {
    String getMessage();

    int getScrollPosition();

    void setScrollPosition(int scrollPosition);

    void setMessage(String message);

    void setWordWrap(boolean wordWrap);

    boolean isWordWrap();
}
