package com.drartgames.stepper;

import java.awt.*;

public class DefaultManifestBuilder implements ManifestBuilder {
    private Version requiredSLVersion;
    private String initSceneName, questName;
    private Dimension resolution;

    //@todo add arguments checks everywhere
    @Override
    public void setRequiredSLVersion(Version version) {
        requiredSLVersion = version;
    }

    @Override
    public void setInitSceneName(String sceneName) {
        initSceneName = sceneName;
    }

    @Override
    public void setQuestName(String name) {
        questName = name;
    }

    @Override
    public void setResolution(Dimension size) {
        resolution = size;
    }

    @Override
    public Manifest build() {
        return new DefaultManifest(requiredSLVersion, initSceneName, questName, resolution);
    }
}
