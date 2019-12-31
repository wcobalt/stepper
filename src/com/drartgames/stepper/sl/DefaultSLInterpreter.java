package com.drartgames.stepper.sl;

import com.drartgames.stepper.DefaultVersion;
import com.drartgames.stepper.Version;
import com.drartgames.stepper.exceptions.NoInitSceneException;
import com.drartgames.stepper.sl.lang.Scene;

import java.util.List;

public class DefaultSLInterpreter implements SLInterpreter {
    private Version version;

    public DefaultSLInterpreter() {
        version = new DefaultVersion("1.0.0:0");
    }

    @Override
    public Version getSLVersion() {
        return version;
    }

    @Override
    public void run() throws NoInitSceneException {

    }

    @Override
    public void loadScenes(List<Scene> scenes) {

    }
}
