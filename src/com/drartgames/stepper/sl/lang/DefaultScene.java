package com.drartgames.stepper.sl.lang;

import com.drartgames.stepper.sl.lang.memory.*;

import java.util.List;

public class DefaultScene implements Scene {
    private String name;
    private List<Action> actions;
    private Manager<Dialog> dialogsManager;
    private Manager<Counter> countersManager;
    private Manager<AudioResource> audioResourcesManager;
    private Manager<PictureResource> pictureResourcesManager;

    public DefaultScene(String name, List<Action> actions) {
        this.name = name;
        this.actions = actions;

        dialogsManager = new DefaultManager<>();
        countersManager = new DefaultManager<>();
        audioResourcesManager = new DefaultManager<>();
        pictureResourcesManager = new DefaultManager<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    @Override
    public Action getActionByName(String name) {
        for (Action action : actions) {
            if (action.getName().equals(name))
                return action;
        }

        return null;
    }

    @Override
    public Manager<Dialog> getDialogsManager() {
        return dialogsManager;
    }

    @Override
    public Manager<Counter> getCountersManager() {
        return countersManager;
    }

    @Override
    public Manager<AudioResource> getAudioResourceManager() {
        return audioResourcesManager;
    }

    @Override
    public Manager<PictureResource> getPictureResourceManager() {
        return pictureResourcesManager;
    }
}
