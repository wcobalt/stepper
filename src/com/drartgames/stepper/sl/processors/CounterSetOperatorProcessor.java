package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.LookupResult;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.Counter;
import com.drartgames.stepper.sl.lang.memory.DefaultCounter;
import com.drartgames.stepper.sl.lang.memory.Manager;

import java.util.List;

public class CounterSetOperatorProcessor extends BaseCounterProcessor {
    public final static int COUNTER_SET_ID = 14;
    public final static int ARGS_COUNT = 2;

    public CounterSetOperatorProcessor() {
        super(COUNTER_SET_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArgumentsCount(operator, ARGS_COUNT);
        checkArgumentNumber(operator, 0, ValueType.STRING_LITERAL);
        checkArgumentNumber(operator, 1, ValueType.STRING_LITERAL, ValueType.INTEGRAL_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String counterName = arguments.get(0).getValue().getStringValue();
        LookupResult lookupResult = lookUpUtil.lookup(interpreter, counterName);

        Manager<Counter> counterManager = lookupResult.getScene().getCountersManager();
        Counter counter = counterManager.getByName(lookupResult.getEntityName());

        if (counter == null) {
            counter = new DefaultCounter(counterName, 0);

            counterManager.add(counter);
        }

        counter.setCounter(getValue(interpreter, arguments.get(1)));
    }
}