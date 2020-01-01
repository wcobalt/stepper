package com.drartgames.stepper.display;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DefaultDisplayToolkit implements DisplayToolkit {
    private Display display;

    private DisplayToolkitState state;

    private Picture defaultPicture;

    public DefaultDisplayToolkit(Display display) {
        this.display = display;

        defaultPicture = new DefaultPicture(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        Graphics g = defaultPicture.getImage().getGraphics();

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 0, 0);

        g.dispose();
    }

    @Override
    public void init(InputWork inputWork) {
        ImageDescriptor backgroundImage = display.addPicture(defaultPicture, 1.0f, 0.0f, 0.0f);
        TextDescriptor mainText = display.addText("", 1.0f, 0.28f, 0.0f, 0.7f);
        mainText.setWordWrap(true);

        InputDescriptor mainInput = display.addInput(1.0f, 0.02f, 0.0f, 0.98f);

        state = new DefaultDisplayToolkitState(backgroundImage, mainText, mainInput, inputWork);
    }

    @Override
    public void setState(DisplayToolkitState state) {
        this.state = state;
    }

    @Override
    public DisplayToolkitState getState() {
        return state;
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public void showMessage(String message, Picture picture) {

    }

    @Override
    public void showPrompt(String message, Picture picture, InputWork inputWork) {
        InputDescriptor input = display.addInput(0.1f, 0.01f, 0.45f, 0.445f);
        ImageDescriptor background = display.addPicture(defaultPicture, 0.11f, 0.445f, 0.445f);
    //    TextDescriptor
    }

    @Override
    public void setBackground(Picture picture) {
        state.getBackgroundDescriptor().setPicture(picture);
    }

    @Override
    public void setText(String message) {
        state.getMainTextDescriptor().setMessage(message);
    }
}
