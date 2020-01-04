package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class SetBackgroundOperatorProcessor implements OperatorProcessor {
    public static final int SET_BACKGROUND_ID = 4;
    public static final int ARGS_COUNT = 1;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println(operator.getName());

    }

    @Override
    public int getOperatorId() {
        return SET_BACKGROUND_ID;
    }
}
