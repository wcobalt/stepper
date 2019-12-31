package com.drartgames.stepper.display;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DefaultTextRenderer extends BaseRenderer implements TextRenderer {
    private static final int GAP_BETWEEN_LINES = 5;

    @Override
    public void render(TextDescriptor descriptor) {
        Display display = descriptor.getDisplay();

        if (display instanceof JFrameDisplay) {
            JFrameDisplay jFrameDisplay = (JFrameDisplay)display;

            Dimension renderResolution = jFrameDisplay.getRenderResolution();

            int finalWidth = (int)(renderResolution.width * descriptor.getWidth());
            int finalHeight = (int)(renderResolution.height * descriptor.getHeight());

            int finalX = (int)(renderResolution.width * descriptor.getX());
            int finalY = (int)(renderResolution.height * descriptor.getY());

            BufferedImage bufferedImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);
            Font renderFont = jFrameDisplay.getRenderFont();

            Graphics bg = bufferedImage.getGraphics();
            bg.setFont(renderFont);

            java.util.List<String> lines;

            if (descriptor.isWordWrap())
                lines = processLines(descriptor.getMessage(), renderFont, finalWidth);
            else {
                lines = new ArrayList<>();
                lines.add(descriptor.getMessage());
            }

            renderText(bg, lines, renderFont, descriptor, finalHeight);

            bg.dispose();

            Graphics g = jFrameDisplay.getRenderBuffer().getGraphics();

            g.drawImage(bufferedImage, finalX, finalY, finalWidth, finalHeight, null);

            g.dispose();
        } else
            throw new IllegalArgumentException("This renderer does not support non-JFrameDisplay displays");
    }

    private java.util.List<String> processLines(String message, Font font, int finalWidth) {
        FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);

        message += "\n"; //for adding last line to lines

        java.util.List<String> lines = new ArrayList<>();
        int messageSize = message.length();

        String currentLine = "", currentWord = "";
        int currentWidth = 0;

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
                    Rectangle2D bounds = font.getStringBounds(" " + currentWord, frc);

                    int width = (int) (bounds.getWidth());

                    currentWidth += width;

                    if (currentWidth > finalWidth) {
                        lines.add(currentLine);

                        currentLine = currentWord;
                        currentWidth = width;
                    } else
                        currentLine += " " + currentWord;

                    //end of word
                    currentWord = "";

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

    private void renderText(Graphics g, java.util.List<String> lines, Font font, TextDescriptor descriptor, int finalHeight) {
        if (!lines.isEmpty()) {
            int scrollOffset = descriptor.getScrollPosition();

            if (scrollOffset >= lines.size())
                scrollOffset = lines.size() - 1;
            else if (scrollOffset < 0)
                scrollOffset = 0;

            descriptor.setScrollPosition(scrollOffset);//@fixme that's not good

            g.setFont(font);

            //render
            FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
            int lineHeight = (int) font.getStringBounds("TEST STRING", frc).getHeight();

            for (int i = scrollOffset; i < lines.size(); i++) {
                int y = (i - scrollOffset) * (lineHeight + GAP_BETWEEN_LINES) + lineHeight;

                g.drawString(lines.get(i), 0, y);

                if (y - lineHeight > finalHeight)
                    break;
            }
        }
    }
}
