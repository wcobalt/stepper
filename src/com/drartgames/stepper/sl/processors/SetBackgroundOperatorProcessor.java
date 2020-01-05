package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.PictureResource;

public class SetBackgroundOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int SET_BACKGROUND_ID = 4;
    public static final int ARGS_COUNT = 1;

    public SetBackgroundOperatorProcessor() {
        super(SET_BACKGROUND_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL);

        String pictureResourceName = operator.getArguments().get(0).getValue().getStringValue();

        PictureResource pictureResource = lookUpUtil.lookupPicture(interpreter, pictureResourceName);

        interpreter.getToolkit().setBackground(pictureResource.getPicture());
    }
}
