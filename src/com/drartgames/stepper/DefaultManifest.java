package com.drartgames.stepper;

import java.awt.*;

public class DefaultManifest implements Manifest {
    private Version requiredSLVersion;
    private String initSceneName, questName;
    private Dimension resolution;

    public DefaultManifest(Version requiredSLVersion, String initSceneName, String questName, Dimension resolution) {
        this.requiredSLVersion = requiredSLVersion;
        this.initSceneName = initSceneName;
        this.questName = questName;
        this.resolution = resolution;
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
}
