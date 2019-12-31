package com.drartgames.stepper.sl.lang;

import java.util.List;

public interface Action {
    List<Operator> getOperators();

    String getName();
}
