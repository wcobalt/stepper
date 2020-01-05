package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.ConditionalOperator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.Tag;

public class IfTagOperatorProcessor extends BaseConditionalProcessor {
    public static final int IF_TAG_ID = 6;
    public static final int IF_TAG_N_ID = 7;
    public static final int ARGS_COUNT = 1;

    private boolean isInverted;

    public IfTagOperatorProcessor(boolean isInverted) {
        super(isInverted ? IF_TAG_N_ID : IF_TAG_ID);

        this.isInverted = isInverted;
    }

    @Override
    protected boolean calculateCondition(SLInterpreter interpreter, ConditionalOperator operator) throws SLRuntimeException {
        checkArguments(operator, false, ValueType.STRING_LITERAL);

        String tagName = operator.getArguments().get(0).getValue().getStringValue();

        Tag tag = interpreter.getTagsManager().getByName(tagName);

        return  ((tag != null && !isInverted) || (tag == null && isInverted));
    }
}
