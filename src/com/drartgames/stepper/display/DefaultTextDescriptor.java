package com.drartgames.stepper.display;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DefaultTextDescriptor extends BaseFigureDescriptor implements TextDescriptor {
    private static final Logger logger = Logger.getLogger(DefaultTextDescriptor.class.getName());

    private String message;
    private int scrollPosition;
    private boolean wordWrap;
    private List<String> lines;

    public DefaultTextDescriptor(Display display, String message, float width, float height, float x, float y) {
        super(display, x, y, width, height);

        this.wordWrap = false;
        scrollPosition = 0;

        setMessage(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getScrollPosition() {
        return scrollPosition;
    }

    @Override
    public void setScrollPosition(int scrollPosition) {
        if (scrollPosition >= lines.size())
            this.scrollPosition = lines.size() - 1;
        else if (scrollPosition < 0)
            this.scrollPosition = 0;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;

        recomputeLines();
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);

        recomputeLines();
    }

    @Override
    public void setWordWrap(boolean wordWrap) {
        this.wordWrap = wordWrap;

        recomputeLines();
    }

    private void recomputeLines() {
        boolean isJFrameDisplay = getDisplay() instanceof  JFrameDisplay;

        if (isJFrameDisplay && wordWrap) {
            int width = (int)(getWidth() * getDisplay().getRenderResolution().width);

            lines = processLines(message, ((JFrameDisplay)getDisplay()).getRenderFont(), width);
        } else {
            lines = new ArrayList<>();
            lines.add(message.replaceAll("\n", " "));

            if (!isJFrameDisplay)
                logger.warning("Display is not a JFrameDisplay. Unable to process lines");
        }
    }

    @Override
    public boolean isWordWrap() {
        return wordWrap;
    }

    @Override
    public String toString() {
        return super.toString() + " SP: " + scrollPosition + " WW: " + wordWrap + " M: " + message.replaceAll("\n", "\\n");
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    private java.util.List<String> processLines(String message, Font font, int finalWidth) {
        FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);

        message += "\n"; //for adding last line to lines

        java.util.List<String> lines = new ArrayList<>();
        int messageSize = message.length();

        String currentLine = "", currentWord = "";
        int currentWidth = 0;
        boolean isFirstWord = true;

        for (int i = 0; i < messageSize; i++) {
            char ch = message.charAt(i);

            boolean doBreakLine = false;

            if (ch == '\n')
                doBreakLine = true;
            else if (ch == '-')
                currentWord += '-';

            switch (ch) {
                case ' ':
                case '-':
                case '\n':
                case '\t':
                    String isFirst = (isFirstWord ? "" : " ");

                    Rectangle2D bounds = font.getStringBounds(isFirst + currentWord, frc);

                    int width = (int) (bounds.getWidth());

                    currentWidth += width;

                    if (currentWidth > finalWidth) {
                        lines.add(currentLine);

                        currentLine = currentWord;
                        currentWidth = width;
                    } else
                        currentLine += (isFirst + currentWord);

                    //end of word
                    currentWord = "";

                    if (isFirstWord) isFirstWord = false;

                    break;
                default:
                    currentWord += ch;
            }

            if (doBreakLine) {
                lines.add(currentLine);

                currentWidth = 0;
                currentLine = "";
                currentWord = "";
            }
        }

        return lines;
    }
}
