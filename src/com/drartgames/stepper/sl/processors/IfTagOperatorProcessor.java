package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class IfTagOperatorProcessor implements OperatorProcessor {
    public static final int IF_TAG_ID = 6;
    public static final int IF_TAG_N_ID = 7;
    public static final int ARGS_COUNT = 1;

    private boolean isInverted;

    public IfTagOperatorProcessor(boolean isInverted) {
        this.isInverted = isInverted;
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {

    }

    @Override
    public int getOperatorId() {
        return isInverted ? IF_TAG_N_ID : IF_TAG_ID;
    }
}
