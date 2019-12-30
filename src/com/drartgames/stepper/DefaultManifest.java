package com.drartgames.stepper;

public class DefaultManifest implements Manifest {
    private Version requiredSLVersion;
    private String initSceneName;

    public DefaultManifest(Version requiredSLVersion, String initSceneName) {
        this.requiredSLVersion = requiredSLVersion;
        this.initSceneName = initSceneName;
    }

    @Override
    public Version getRequiredSLVersion() {
        return requiredSLVersion;
    }

    @Override
    public String getInitSceneName() {
        return initSceneName;
    }
}
