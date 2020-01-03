package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class LoadImageOperatorProcessor implements OperatorProcessor {
    public static final int LOAD_IMAGE_ID = 1;
    public static final int ARGS_COUNT = 2;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println(operator.getName());

    }

    @Override
    public int getOperatorId() {
        return LOAD_IMAGE_ID;
    }
}
