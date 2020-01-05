package com.drartgames.stepper.display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class DefaultDisplayToolkit implements DisplayToolkit {
    private Display display;
    private DisplayToolkitState state;

    private Picture defaultPicture;

    private InputWork inputWork;

    private final static float PROMPT_GAP = 0.005f;
    private final static float PROMPT_INPUT_HEIGHT = 0.01f;
    private final static float PROMPT_EFFECTIVE_WIDTH = 0.2f;
    private final static float PROMPT_IMAGE_WIDTH = 0.08f;

    public DefaultDisplayToolkit(Display display) {
        this.display = display;

        defaultPicture = new DefaultPicture(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        Graphics g = defaultPicture.getImage().getGraphics();

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 0, 0);

        g.dispose();
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

        return new DefaultDisplayToolkitState(backgroundImage, mainText, mainInput, keyAwaitDescriptor, inputWork);
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

        currentY += input != null ? PROMPT_INPUT_HEIGHT : 0;
        currentY += PROMPT_GAP;

        //offset of prompt begin
        float finalY = (1 - currentY) / 2.0f;

        //background
        //@fixme propm_eff_w + gap -> to const
        BufferedImage blackBackground = new BufferedImage((int)((PROMPT_EFFECTIVE_WIDTH + PROMPT_GAP) * 1000),
                (int)(currentY * 1000), BufferedImage.TYPE_INT_RGB);

        ImageDescriptor background = display.addPicture(new DefaultPicture(blackBackground),
                PROMPT_EFFECTIVE_WIDTH + PROMPT_GAP, middleX - PROMPT_GAP / 2, finalY);

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
