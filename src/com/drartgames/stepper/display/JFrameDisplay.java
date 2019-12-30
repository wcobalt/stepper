package com.drartgames.stepper.display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public interface JFrameDisplay extends Display {
    JFrame getFrame();

    JPanel getRenderPanel();

    BufferedImage getRenderBuffer();

    Font getRenderFont();
}
