package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;

public class ShowOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int SHOW_ID = 8;
    public static final int ARGS_COUNT = 1;

    public ShowOperatorProcessor() {
        super(SHOW_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL);

        String message = operator.getArguments().get(0).getValue().getStringValue();

        interpreter.getToolkit().showMessage(message, null);
    }
}
