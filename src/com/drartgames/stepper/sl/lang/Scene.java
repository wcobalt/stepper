package com.drartgames.stepper.sl.lang;

import com.drartgames.stepper.sl.lang.Action;
import java.util.List;

public interface Scene {
    String getName();

    List<Action> getActions();
}
