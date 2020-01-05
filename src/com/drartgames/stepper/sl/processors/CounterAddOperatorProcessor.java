package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.Counter;

import java.util.List;

public class CounterAddOperatorProcessor extends BaseCounterProcessor {
    public final static int COUNTER_ADD_ID = 15;
    public final static int ARGS_COUNT = 2;

    public CounterAddOperatorProcessor() {
        super(COUNTER_ADD_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArgumentsCount(operator, ARGS_COUNT);
        checkArgumentNumber(operator, 0, ValueType.STRING_LITERAL);
        checkArgumentNumber(operator, 1, ValueType.STRING_LITERAL, ValueType.INTEGRAL_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String counterReference = arguments.get(0).getValue().getStringValue();
        int addValue = getValue(interpreter, arguments.get(1));

        Counter counter = lookUpUtil.lookupCounter(interpreter, counterReference);

        counter.add(addValue);
    }
}
