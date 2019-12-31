package com.drartgames.stepper.sl;

import com.drartgames.stepper.DefaultVersion;
import com.drartgames.stepper.Version;
import com.drartgames.stepper.exceptions.NoInitSceneException;
import com.drartgames.stepper.sl.lang.Scene;

import java.util.List;

public class DefaultSLInterpreter implements SLInterpreter {
    private Version version;

    public static final int LOAD_IMAGE_ID = 1, LOAD_AUDIO_ID = 2, SET_TEXT_ID = 3, SET_BACKGROUND_ID = 4,
                            PLAY_ID = 5, IF_TAG_ID = 6, IF_TAG_N_ID = 7, SHOW_ID = 8, TAG_ID = 9, DISABLE_DIALOG_ID = 10,
                            ENABLE_DIALOG_ID = 11, ADD_DIALOG_ID = 12, CALL_ID = 13;

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
