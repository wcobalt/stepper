package com.drartgames.stepper.display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DefaultDisplayToolkit implements DisplayToolkit {
    private Display display;
    private DisplayToolkitState state;

    private Picture defaultPicture;
    private Picture backTextPicture;

    private InputWork inputWork;

    private final static float PROMPT_GAP = 0.005f;
    private final static float PROMPT_INPUT_HEIGHT = 0.01f;
    private final static float PROMPT_EFFECTIVE_WIDTH = 0.26f;
    private final static float PROMPT_IMAGE_WIDTH = 0.15f;

    public DefaultDisplayToolkit(Display display) {
        this.display = display;

        defaultPicture = new DefaultPicture(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        backTextPicture = new DefaultPicture(new BufferedImage(100, 32, BufferedImage.TYPE_INT_RGB));
    }

    @Override
    public void initialize(InputWork inputWork) {
        this.inputWork = inputWork;

        //state = makeNewState();
    }

    @Override
    public void setState(DisplayToolkitState state) {
        this.state = state;
    }

    @Override
    public DisplayToolkitState makeNewState() {
        ImageDescriptor backgroundImage = display.addPicture(defaultPicture, 1.0f, 0.0f, 0.0f);
        ImageDescriptor backTextImage = display.addPicture(backTextPicture, 1.0f, 0.0f, 0.7f);

        TextDescriptor mainText = display.addText("", 0.99f, 0.28f, 0.005f, 0.7f);
        mainText.setWordWrap(true);

        InputDescriptor mainInput = display.addInput(0.99f, 0.04f, 0.005f, 0.96f);
        mainInput.setIgnoreNewline(true);

        display.getDisplayState().setActiveInput(mainInput);

        KeyAwaitDescriptor keyAwaitDescriptor = display.awaitForKey(KeyEvent.VK_ENTER,
                (descriptor) -> {
                    inputWork.execute(mainInput.getCurrentText());
                    mainInput.setCurrentText("");
                });

        return new DefaultDisplayToolkitState(backgroundImage, mainText, mainInput, keyAwaitDescriptor, backTextImage, inputWork);
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
    public void showMessage(String message, Picture picture, Work work) {
        showPrompt(message, picture, null, work);
    }

    @Override
    public void showPrompt(String message, Picture picture, InputWork inputWork, Work work) {
        //PROMPT SCHEME
        //GAP
        //[IMAGE
        //GAP]?
        //TEXT
        //[GAP
        //INPUT]?
        //GAP

        float middleX = (1 - PROMPT_EFFECTIVE_WIDTH) / 2.0f;
        float currentY = PROMPT_GAP;

        //background
        ImageDescriptor background = display.addPicture(defaultPicture,
                PROMPT_EFFECTIVE_WIDTH + PROMPT_GAP, middleX - PROMPT_GAP / 2, 0);

        //image
        ImageDescriptor image = picture != null ? display.addPicture(picture, PROMPT_IMAGE_WIDTH,
                (1 - PROMPT_IMAGE_WIDTH) / 2.0f, currentY) : null;

        currentY += image != null ? image.getHeight() + PROMPT_GAP : 0;

        //text
        TextDescriptor text = display.addText(message, PROMPT_EFFECTIVE_WIDTH, 0, middleX, currentY);
        text.setWordWrap(true);

        int textHeight = display.getTextRenderer().computeTextHeight(display, text.getLines().size());
        float relTextHeight = textHeight / (float)display.getRenderResolution().height;

        text.setHeight(relTextHeight);

        currentY += relTextHeight + PROMPT_GAP;

        //input
        InputDescriptor input = inputWork != null ? display.addInput(PROMPT_EFFECTIVE_WIDTH, PROMPT_INPUT_HEIGHT,
                middleX, currentY) : null;

        currentY += input != null ? PROMPT_INPUT_HEIGHT + PROMPT_GAP : 0;

        //offset of prompt begin
        float finalY = (1 - 0.28f - currentY) / 2.0f;

        Dimension resolution = display.getRenderResolution();
        //@fixme propm_eff_w + gap -> to const
        BufferedImage blackBackground = new BufferedImage((int)((PROMPT_EFFECTIVE_WIDTH + PROMPT_GAP) * resolution.width),
                (int)(currentY * resolution.height), BufferedImage.TYPE_INT_RGB);

        background.setPicture(new DefaultPicture(blackBackground));
        background.setY(finalY);

        //shift prompt
        text.setY(text.getY() + finalY);

        if (image != null)
            image.setY(image.getY() + finalY);

        if (input != null)
            input.setY(input.getY() + finalY);

        //move focus to input
        if (input != null)
            display.getDisplayState().setActiveInput(input);

        display.awaitForKey(KeyEvent.VK_ENTER, (descriptor) -> {
            descriptor.setDoFree(true);
            background.setDoFree(true);

            text.setDoFree(true);

            if (image != null)
                image.setDoFree(true);

            if (input != null) {
                inputWork.execute(input.getCurrentText());
                input.setDoFree(true);
                display.getDisplayState().setActiveInput(state.getMainInputDescriptor());
            }

            if (work != null)
                work.execute();
        });
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
