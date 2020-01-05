package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.DefaultTag;
import com.drartgames.stepper.sl.lang.memory.Tag;

public class TagOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int TAG_ID = 9;
    public static final int ARGS_COUNT = 1;

    public TagOperatorProcessor() {
        super(TAG_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL);

        String tagName = operator.getArguments().get(0).getValue().getStringValue();
        Tag tag = new DefaultTag(tagName);

        interpreter.getTagsManager().add(tag);
    }
}
