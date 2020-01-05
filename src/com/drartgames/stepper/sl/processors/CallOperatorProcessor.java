package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;

public class CallOperatorProcessor extends BaseProcessor {
    public static final int CALL_ID = 13;
    public static final int ARGS_COUNT = 1;

    public CallOperatorProcessor() {
        super(CALL_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, false, ValueType.GENERAL_LITERAL);

        String actionName = operator.getArguments().get(0).getValue().getGeneralLiteralValue().toString();

        Action action = lookUpUtil.lookupAction(interpreter, actionName);

        interpreter.executeAction(action);
    }
}
