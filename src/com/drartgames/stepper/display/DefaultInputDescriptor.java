package com.drartgames.stepper.display;

public class DefaultInputDescriptor extends BaseFigureDescriptor implements InputDescriptor {
    private String currentText;
    private boolean ignoreNewline;

    public DefaultInputDescriptor(Display display, float width, float height, float x, float y) {
        super(display, x, y, width, height);

        currentText = "";
        ignoreNewline = false;
    }

    @Override
    public void setIgnoreNewline(boolean ignoreNewline) {
        this.ignoreNewline = ignoreNewline;
    }

    @Override
    public boolean isIgnoreNewline() {
        return ignoreNewline;
    }

    @Override
    public String getCurrentText() {
        return currentText;
    }

    @Override
    public void setCurrentText(String text) {
        this.currentText = text;
    }

    @Override
    public void addChar(char ch) {//@fixme this method has nothing common with directly DESCRIPTOR, it needs some kind of InputHandler class
        if (!ignoreNewline || ch != '\n')
            currentText += ch;
    }

    @Override
    public String toString() {
        return super.toString() + " I: " + currentText.replaceAll("\n", "\\n") + " IN: " + ignoreNewline;
    }
}
