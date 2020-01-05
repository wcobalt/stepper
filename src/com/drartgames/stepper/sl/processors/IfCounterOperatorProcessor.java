package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.ConditionalOperator;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfCounterOperatorProcessor extends BaseConditionalProcessor {
    public final static int IF_COUNTER_ID = 17;
    public final static int ARGS_COUNT = 3;

    private final static String EQUALS = "eq";
    private final static String GREATER = "gr";
    private final static String LESSER = "ls";
    private final static String NOT = "_n";

    private Pattern pattern;

    private class CounterUtil extends BaseCounterProcessor {
        public CounterUtil(int operatorId) {
            super(operatorId);
        }

        @Override
        public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException { }
    }

    public IfCounterOperatorProcessor() {
        super(IF_COUNTER_ID);

        pattern = Pattern.compile("(" + EQUALS + "|" + LESSER + "|" + GREATER + ")(" + NOT + ")?");
    }

    @Override
    protected boolean calculateCondition(SLInterpreter interpreter, ConditionalOperator operator) throws SLRuntimeException {
        checkArgumentsCount(operator, ARGS_COUNT);
        checkArgumentNumber(operator, 0, ValueType.GENERAL_LITERAL);
        checkArgumentNumber(operator, 1, ValueType.INTEGRAL_LITERAL, ValueType.STRING_LITERAL);
        checkArgumentNumber(operator, 2, ValueType.INTEGRAL_LITERAL, ValueType.STRING_LITERAL);

        List<Argument> arguments = operator.getArguments();
        CounterUtil util = new CounterUtil(0);

        int value1 = util.getValue(interpreter, arguments.get(1));
        int value2 = util.getValue(interpreter, arguments.get(2));

        Matcher matcher = pattern.matcher(arguments.get(0).getValue().getGeneralLiteralValue().toString());

        if (matcher.find()) {
            String mainCond = matcher.group(1);
            String inversion = matcher.group(2);

            boolean isInverted = false;

            if (inversion != null) isInverted = true;

            boolean result = false;

            switch (mainCond) {
                case LESSER:
                    result = (value1 < value2);

                    break;
                case GREATER:
                    result = (value1 > value2);

                    break;
                case EQUALS:
                    result = (value1 == value2);

                    break;
            }

            return isInverted ? !result : result;
        } else
            throw new SLRuntimeException("Unexpected conditional modifier at line " + operator.getCallLineNumber());
    }
}
