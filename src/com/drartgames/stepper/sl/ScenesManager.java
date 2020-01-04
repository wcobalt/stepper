package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.NoInitSceneException;
import com.drartgames.stepper.sl.lang.Scene;

import java.util.List;

public interface ScenesManager {
    Scene getCurrentScene();

    Scene getInitScene();

    void setInitScene(Scene scene) throws NoInitSceneException;

    void setCurrentScene(Scene scene);

    Scene getSceneByName(String name);

    List<Scene> getScenes();
}
