package com.drartgames.stepper.display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DefaultPicture implements Picture {
    private BufferedImage bufferedImage;

    public DefaultPicture(String file) throws IOException  {
        File imageFile = new File(file);

        bufferedImage = ImageIO.read(imageFile);
    }

    public DefaultPicture(BufferedImage image) {
        bufferedImage = image;
    }

    @Override
    public BufferedImage getImage() {
        return bufferedImage;
    }
}
