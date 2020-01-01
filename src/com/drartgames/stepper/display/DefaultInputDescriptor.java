package com.drartgames.stepper.display;

public class DefaultInputDescriptor extends BaseFigureDescriptor implements InputDescriptor {
    private String currentText;

    public DefaultInputDescriptor(Display display, float width, float height, float x, float y) {
        super(display, x, y, width, height);

        currentText = "";
    }

    @Override
    public String getCurrentText() {
        return currentText;
    }

    @Override
    public void setCurrentText(String text) {
        this.currentText = text;
    }
}
