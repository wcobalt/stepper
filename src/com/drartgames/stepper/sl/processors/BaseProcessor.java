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

    protected void checkArguments(Operator operator, ValueType... types) throws SLRuntimeException  {
        List<Argument> arguments = operator.getArguments();

        if (types.length != arguments.size())
            throw new SLRuntimeException("Arguments count mismatch: expected - " + types.length +
                    ", found - " + arguments.size() + " at line " + operator.getCallLineNumber());
        else {
            int i = 0;

            for (Argument argument : arguments) {
                ValueType valueType = argument.getValue().getValueType();

                if (valueType != types[i])
                    throw new SLRuntimeException(i + "th argument type mismatch: " +
                            typeName(types[i]) + " expected, " + typeName(valueType) + " found");

                i++;
            }
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
            default:
                return "undefined_type";
        }
    }
}
