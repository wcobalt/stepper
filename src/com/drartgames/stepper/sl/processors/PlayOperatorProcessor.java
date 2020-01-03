package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class PlayOperatorProcessor implements OperatorProcessor {
    public static final int PLAY_ID = 5;
    public static final int ARGS_COUNT = 2;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println(operator.getName());
    }

    @Override
    public int getOperatorId() {
        return PLAY_ID;
    }
}
