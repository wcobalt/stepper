package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.DefaultLookupUtil;
import com.drartgames.stepper.sl.LookUpUtil;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;

import java.util.List;

public abstract class BaseProcessor implements OperatorProcessor {
    private int operatorId;
    protected LookUpUtil lookUpUtil;

    public BaseProcessor(int operatorId) {
        this.operatorId = operatorId;

        lookUpUtil = new DefaultLookupUtil();
    }

    @Override
    public int getOperatorId() {
        return operatorId;
    }

    protected void checkArguments(Operator operator, boolean doIgnoreArgumentsCount, ValueType... types) throws SLRuntimeException  {
        List<Argument> arguments = operator.getArguments();

        if (doIgnoreArgumentsCount || checkArgumentsCount(operator, types.length)) {
            int i = 0;

            for (ValueType type : types) {
                ValueType valueType = arguments.get(i).getValue().getValueType();

                if (valueType != type)
                    throw new SLRuntimeException(i + "th argument type mismatch: " +
                            typeName(type) + " expected, " + typeName(valueType) + " found");

                i++;
            }
        }
    }

    protected boolean checkArgumentsCount(Operator operator, int expectedCount) throws SLRuntimeException {
        List<Argument> arguments = operator.getArguments();

        if (arguments.size() != expectedCount) {
            throw new SLRuntimeException("Arguments count mismatch: expected - " + expectedCount +
                    ", found - " + arguments.size() + " at line " + operator.getCallLineNumber());
        } else
            return true;
    }

    //doesnt make any checks for arguments count
    protected void checkArgumentNumber(Operator operator, int number, ValueType... allowedTypes) throws SLRuntimeException {
        Argument argument = operator.getArguments().get(number);
        ValueType valueType = argument.getValue().getValueType();

        boolean found = false;

        for (ValueType type : allowedTypes) {
            if (valueType == type) {
                found = true;

                break;
            }
        }

        if (!found) {
            String possibleTypes = "";

            for (ValueType type : allowedTypes)
                possibleTypes += " > " + typeName(type) + "\n";

            throw new SLRuntimeException(number + "th argument's type mismatch: " + typeName(valueType) +
                    " found, meanwhile expected one of the following:\n" + possibleTypes);
        }
    }

    private String typeName(ValueType valueType) {
        switch (valueType) {
            case INTEGRAL_LITERAL:
                return "<number_literal>";
            case STRING_LITERAL:
                return "<string_literal>";
            case GENERAL_LITERAL:
                return "<general_literal>";
            case FLOAT_LITERAL:
                return "<float_literal>";
            case BOOL_LITERAL:
                return "<bool_literal>";
            case NONE_LITERAL:
                return "<none_literal>";
            default:
                return "undefined_type";//impossible
        }
    }
}
