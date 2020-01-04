package com.drartgames.stepper.display;

import java.util.List;

public interface TextDescriptor extends FigureDescriptor {
    String getMessage();

    int getScrollPosition();

    void setScrollPosition(int scrollPosition);

    void setMessage(String message);

    void setWordWrap(boolean wordWrap);

    boolean isWordWrap();

    List<String> getLines();
}
