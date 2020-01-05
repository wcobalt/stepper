package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.Dialog;

//@todo merge enableDialog disableDialog in some kind of switchDialog/setDialog/turnDialog/etc
public class DisableDialogOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int DISABLE_DIALOG_ID = 10;
    public static final int ARGS_COUNT = 1;

    public DisableDialogOperatorProcessor() {
        super(DISABLE_DIALOG_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL);

        String reference = operator.getArguments().get(0).getValue().getStringValue();

        Dialog dialog = lookUpUtil.lookupDialog(interpreter, reference);

        dialog.setEnabled(false);
    }
}
