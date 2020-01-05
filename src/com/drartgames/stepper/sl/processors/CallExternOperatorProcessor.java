package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;

import java.util.List;

public class CallExternOperatorProcessor extends BaseProcessor {
    public final static int CALL_EXTERN_ID = 16;
    public final static int ARGS_COUNT = 2;

    public CallExternOperatorProcessor() {
        super(CALL_EXTERN_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, false, ValueType.GENERAL_LITERAL, ValueType.GENERAL_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String reference = arguments.get(0).getValue().getGeneralLiteralValue().toString() + "." +
                           arguments.get(1).getValue().getGeneralLiteralValue().toString();

        Action action = lookUpUtil.lookupAction(interpreter, reference);

        interpreter.executeAction(action);
    }
}
