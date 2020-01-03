package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class SetTextOperatorProcessor implements OperatorProcessor {
    public static final int SET_TEXT_ID = 3;
    public static final int ARGS_COUNT = 1;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println(operator.getName());

    }

    @Override
    public int getOperatorId() {
        return SET_TEXT_ID;
    }
}
