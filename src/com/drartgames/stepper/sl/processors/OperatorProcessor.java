package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public interface OperatorProcessor {
    void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException;

    int getOperatorId();
}
