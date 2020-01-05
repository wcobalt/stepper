package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.Dialog;

import java.util.List;

public class SwitchDialogOperatorProcessor extends BaseProcessor {
    public static final int SWITCH_DIALOG_ID = 10;
    public static final int ARGS_COUNT = 2;

    public SwitchDialogOperatorProcessor() {
        super(SWITCH_DIALOG_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL, ValueType.BOOL_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String reference = arguments.get(0).getValue().getStringValue();
        boolean enable = arguments.get(1).getValue().getBoolValue();

        Dialog dialog = lookUpUtil.lookupDialog(interpreter, reference);

        dialog.setEnabled(enable);
    }
}
