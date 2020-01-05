package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;

public class SetTextOperatorProcessor extends BaseProcessor {
    public static final int SET_TEXT_ID = 3;
    public static final int ARGS_COUNT = 1;

    public SetTextOperatorProcessor() {
        super(SET_TEXT_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, false, ValueType.STRING_LITERAL);

        String text = operator.getArguments().get(0).getValue().getStringValue();

        interpreter.getToolkit().setText(text);
    }
}
