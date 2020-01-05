package com.drartgames.stepper.display;

import java.util.HashMap;
import java.util.Map;

public class DefaultInputRenderer extends BaseRenderer implements InputRenderer {
    private Map<InputDescriptor, TextDescriptor> texts;

    public DefaultInputRenderer() {
        texts = new HashMap<>();
    }

    @Override
    public void render(InputDescriptor inputDescriptor) {
        TextDescriptor textDescriptor = texts.get(inputDescriptor);

        if (textDescriptor == null) {
            textDescriptor = inputDescriptor.getDisplay().addText("", 0, 0, 0, 0);
            texts.put(inputDescriptor, textDescriptor);
        }

        synchronize(inputDescriptor, textDescriptor);
    }

    private void synchronize(InputDescriptor input, TextDescriptor text) {
        text.setMessage(input.getCurrentText());
        text.setX(input.getX());
        text.setY(input.getY());
        text.setWidth(input.getWidth());
        text.setHeight(input.getHeight());
        text.setVisible(input.isVisible());
    }
}
