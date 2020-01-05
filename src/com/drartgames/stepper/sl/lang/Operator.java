package com.drartgames.stepper.sl.lang;

import java.util.List;

public interface Operator {
    String getName();

    int getOperatorId();

    List<Argument> getArguments();

    int getCallLineNumber();
}
