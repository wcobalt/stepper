package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.Dialog;

public class GotoOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int GOTO_ID = 11;
    public static final int ARGS_COUNT = 1;

    public GotoOperatorProcessor() {
        super(GOTO_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.GENERAL_LITERAL);

        String reference = operator.getArguments().get(0).getValue().getGeneralLiteralValue().toString();

        Scene scene = interpreter.getScenesManager().getSceneByName(reference);

        interpreter.gotoScene(scene);
    }
}
