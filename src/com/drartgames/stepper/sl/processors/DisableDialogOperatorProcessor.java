package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class DisableDialogOperatorProcessor implements OperatorProcessor {
    public static final int DISABLE_DIALOG_ID = 10;
    public static final int ARGS_COUNT = 1;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {

    }

    @Override
    public int getOperatorId() {
        return DISABLE_DIALOG_ID;
    }
}