package com.drartgames.stepper;

import java.awt.*;

public interface ManifestBuilder {
    void setRequiredSLVersion(Version version);

    void setInitSceneName(String sceneName);

    void setQuestName(String name);

    void setResolution(Dimension size);

    Manifest build();
}
