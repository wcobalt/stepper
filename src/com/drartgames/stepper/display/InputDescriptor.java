package com.drartgames.stepper.display;

public interface InputDescriptor extends FigureDescriptor {
    void setIgnoreNewline(boolean ignoreNewline);

    boolean isIgnoreNewline();

    String getCurrentText();

    void setCurrentText(String text);

    void addChar(char ch);
}
