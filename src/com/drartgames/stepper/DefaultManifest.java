package com.drartgames.stepper;

import java.awt.*;

public class DefaultManifest implements Manifest {
    private Version requiredSLVersion;
    private String initSceneName, questName, fontName;
    private Dimension resolution;
    private int fontSize;

    public DefaultManifest(Version requiredSLVersion, String initSceneName, String questName, Dimension resolution,
                           int fontSize, String fontName) {
        this.requiredSLVersion = requiredSLVersion;
        this.initSceneName = initSceneName;
        this.questName = questName;
        this.resolution = resolution;
        this.fontSize = fontSize;
        this.fontName = fontName;
    }

    @Override
    public Version getRequiredSLVersion() {
        return requiredSLVersion;
    }

    @Override
    public String getInitSceneName() {
        return initSceneName;
    }

    @Override
    public String getQuestName() {
        return questName;
    }

    @Override
    public Dimension getResolution() {
        return resolution;
    }

    @Override
    public int getFontSize() {
        return fontSize;
    }

    @Override
    public String getFontName() {
        return fontName;
    }
}
