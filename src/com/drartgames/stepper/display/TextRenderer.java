package com.drartgames.stepper.display;

public interface TextRenderer extends Renderer {
    void render(TextDescriptor descriptor);

    int computeTextHeight(Display display, int linesCount);
}
