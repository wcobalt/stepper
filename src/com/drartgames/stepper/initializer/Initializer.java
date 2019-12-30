package com.drartgames.stepper.initializer;

import java.io.File;
import java.util.List;

public interface Initializer {
    void initialize(String... args);

    void run();

    void log(String message);

    void logn(String message);

    List<ParameterHandler> getParametersHandlers();

    void setCurrentQuestName(String questName);

    void setQuestsDirectory(File directory);

    String getCurrentQuestName();

    File getQuestsDirectory();
}
