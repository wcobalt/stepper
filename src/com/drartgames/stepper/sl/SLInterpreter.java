package com.drartgames.stepper.sl;

import com.drartgames.stepper.Version;
import com.drartgames.stepper.exceptions.NoInitSceneException;

public interface SLInterpreter {
    Version getSLVersion();

    void run() throws NoInitSceneException;
}
