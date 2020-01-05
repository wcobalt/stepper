package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Value;
import com.drartgames.stepper.sl.lang.memory.Counter;
public abstract class BaseCounterProcessor extends BaseProcessor {
    public BaseCounterProcessor(int operatorId) {
        super(operatorId);
    }

    //get value of counter OR integral literal
    protected int getValue(SLInterpreter interpreter, Argument argument) throws SLRuntimeException {
        Value value = argument.getValue();

        switch (value.getValueType()) {
            case STRING_LITERAL:
                Counter counter = lookUpUtil.lookupCounter(interpreter, value.getStringValue());

                return counter.getCounter();

            case INTEGRAL_LITERAL:
                return argument.getValue().getIntegralValue();

            default:
                throw new IllegalArgumentException("Unexpected type of value. Either string literal or integral" +
                        " literal have been expected");
        }
    }
}
