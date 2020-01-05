package com.drartgames.stepper.sl.analyzer;

import com.drartgames.stepper.sl.SLInterpreter;

import java.io.File;

public interface ScriptLoaderFacade {
    void load(SLInterpreter slInterpreter, File scenesDirectory, ScriptsLoaderFacadeObserverWork work);
}
