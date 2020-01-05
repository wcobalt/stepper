package com.drartgames.stepper.display;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DefaultImageRenderer extends BaseRenderer implements ImageRenderer {
    @Override
    public void render(ImageDescriptor descriptor) {
        if (descriptor.isVisible()) {
            Display display = descriptor.getDisplay();

            if (display instanceof JFrameDisplay) {
                JFrameDisplay jFrameDisplay = (JFrameDisplay) display;

                BufferedImage image = descriptor.getPicture().getImage();

                int originalWidth = image.getWidth();
                int originalHeight = image.getHeight();

                float width = descriptor.getWidth();
                float x = descriptor.getX(), y = descriptor.getY();
                Dimension renderResolution = jFrameDisplay.getRenderResolution();

                int finalWidth, finalHeight, finalX, finalY;

                finalWidth = (int) (renderResolution.width * width);
                finalHeight = (int) (finalWidth / (float) originalWidth * originalHeight);
                finalX = (int) (x * renderResolution.width);
                finalY = (int) (y * renderResolution.height);

                //System.out.println(finalX + " " + finalY + " " + (finalWidth / (float)finalHeight) + " " + (descriptor.getWidth() /descriptor.getHeight()));
                Graphics g = jFrameDisplay.getRenderBuffer().getGraphics();

                g.drawImage(descriptor.getPicture().getImage(), finalX, finalY, finalWidth, finalHeight, null);

                g.dispose();
            } else
                throw new IllegalArgumentException("This renderer does not support non-JFrameDisplay displays");
        }
    }
}
