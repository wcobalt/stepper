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
        if (descriptor.isVisible()) {
            Display display = descriptor.getDisplay();

            if (display instanceof JFrameDisplay) {
                JFrameDisplay jFrameDisplay = (JFrameDisplay) display;

                Dimension renderResolution = jFrameDisplay.getRenderResolution();

                int finalWidth = (int) (renderResolution.width * descriptor.getWidth());
                int finalHeight = (int) (renderResolution.height * descriptor.getHeight());

                int finalX = (int) (renderResolution.width * descriptor.getX());
                int finalY = (int) (renderResolution.height * descriptor.getY());

                BufferedImage bufferedImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);
                Font renderFont = jFrameDisplay.getRenderFont();

                Graphics bg = bufferedImage.getGraphics();
                bg.setFont(renderFont);

                java.util.List<String> lines = descriptor.getLines();

                renderText(bg, lines, renderFont, descriptor, finalHeight);

                bg.dispose();

                Graphics g = jFrameDisplay.getRenderBuffer().getGraphics();

                g.drawImage(bufferedImage, finalX, finalY, finalWidth, finalHeight, null);

                g.dispose();
            } else
                throw new IllegalArgumentException("This renderer does not support non-JFrameDisplay displays");
        }
    }

    private void renderText(Graphics g, java.util.List<String> lines, Font font, TextDescriptor descriptor, int finalHeight) {
        if (!lines.isEmpty()) {
            int scrollOffset = descriptor.getScrollPosition();

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

    @Override
    public int computeTextHeight(Display display, int linesCount) {
        if (display instanceof JFrameDisplay) {
            JFrameDisplay jFrameDisplay = (JFrameDisplay) display;
            Font renderFont = jFrameDisplay.getRenderFont();

            FontRenderContext frc = new FontRenderContext(renderFont.getTransform(), true, true);
            int lineHeight = (int)renderFont.getStringBounds("TEST STRING", frc).getHeight();

            return linesCount * (lineHeight + GAP_BETWEEN_LINES);
        } else
            throw new IllegalArgumentException("This renderer does not support non-JFrameDisplay displays");
    }
}
