package com.drartgames.stepper.initializer;

import com.drartgames.stepper.Manifest;
import com.drartgames.stepper.exceptions.SLVersionMismatchException;
import com.drartgames.stepper.display.Display;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.ScriptLoaderFacade;

import java.io.File;
import java.util.List;

public interface Initializer {
    void initialize(String... args) throws SLVersionMismatchException;

    void run();

    void log(String message);

    void logn(String message);

    List<ParameterHandler> getParametersHandlers();

    void setCurrentQuestName(String questName);

    void setQuestsDirectory(File directory);

    SLInterpreter getInterpreter();

    String getCurrentQuestName();

    File getQuestsDirectory();

    Manifest getManifest();

    ScriptLoaderFacade getScriptLoaderFacade();

    Display getDisplay();

}
