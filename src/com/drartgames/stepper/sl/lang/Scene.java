package com.drartgames.stepper.sl.lang;

import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.memory.*;

import java.util.List;

public interface Scene {
    String getName();

    List<Action> getActions();

    Action getActionByName(String name);

    Manager<Dialog> getDialogsManager();

    Manager<Counter> getCountersManager();

    Manager<AudioResource> getAudioResourcesManager();

    Manager<PictureResource> getPictureResourceManager();
}
