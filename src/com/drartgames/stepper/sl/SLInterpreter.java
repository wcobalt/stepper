package com.drartgames.stepper.sl;

import com.drartgames.stepper.Version;
import com.drartgames.stepper.exceptions.NoInitSceneException;

import java.util.List;

public interface SLInterpreter {
    Version getSLVersion();

    void run() throws NoInitSceneException;

    void loadScenes(List<Scene> scenes);
}
