package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class AddDialogOperatorProcessor implements OperatorProcessor {
    public static final int ADD_DIALOG_ID = 12;
    public static final int ARGS_COUNT = 4;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println(operator.getName());

    }

    @Override
    public int getOperatorId() {
        return ADD_DIALOG_ID;
    }
}
