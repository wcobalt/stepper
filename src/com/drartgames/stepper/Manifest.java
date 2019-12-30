package com.drartgames.stepper;

import java.awt.*;

public interface Manifest {
    Version getRequiredSLVersion();

    String getInitSceneName();

    String getQuestName();

    Dimension getResolution();

    int getFontSize();

    String getFontName();
}
