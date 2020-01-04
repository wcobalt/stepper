package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.NoInitSceneException;
import com.drartgames.stepper.sl.lang.Scene;

import java.util.ArrayList;
import java.util.List;

public class DefaultScenesManager implements ScenesManager {
    private List<Scene> scenes;
    private Scene currentScene, initScene;

    public DefaultScenesManager() {
        scenes = new ArrayList<>();
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }

    @Override
    public Scene getInitScene() {
        return initScene;
    }

    @Override
    public void setInitScene(Scene scene) throws NoInitSceneException {
        for (Scene s : scenes) {
            if (s == scene) {
                initScene = scene;

                return;
            }
        }

        throw new NoInitSceneException("Passed to setCurrentScene() scene wasn't found in scenes list. Add it first.");
    }

    @Override
    public Scene getSceneByName(String name) {
        for (Scene scene : scenes) {
            if (scene.getName().equals(name))
                return scene;
        }

        return null;
    }

    @Override
    public void setCurrentScene(Scene scene) {
        //@todo maybe check presence of scene in scenes list?
        this.currentScene = scene;
    }

    @Override
    public List<Scene> getScenes() {
        return scenes;
    }
}
