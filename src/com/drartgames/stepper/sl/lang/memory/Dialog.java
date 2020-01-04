package com.drartgames.stepper.sl.lang.memory;

import com.drartgames.stepper.sl.lang.Action;

public interface Dialog extends Entity {
    float matches(String input);

    Action getDialogAction();

    void setEnabled(boolean isEnabled);

    boolean isEnabled();
}
