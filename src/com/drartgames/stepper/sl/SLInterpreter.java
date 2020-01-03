package com.drartgames.stepper.sl;

import com.drartgames.stepper.Version;
import com.drartgames.stepper.display.Display;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.lang.memory.Manager;
import com.drartgames.stepper.sl.lang.memory.Tag;

import java.util.List;

public interface SLInterpreter {
    Version getSLVersion();

    Display getDisplay();

    void run() throws SLRuntimeException;

    void initialize() throws SLRuntimeException;

    void executeAction(Action action) throws SLRuntimeException;

    void executeOperators(List<Operator> operatorList) throws SLRuntimeException;

    void gotoScene(Scene scene) throws SLRuntimeException;

    ScenesManager getScenesManager();

    Manager<Tag> getTagsManager();

    boolean isFlagSet(int flag);

    void setFlag(int flag, boolean value);
}
