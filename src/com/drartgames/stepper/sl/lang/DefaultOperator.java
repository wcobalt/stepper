package com.drartgames.stepper.sl.lang;

import java.util.List;

public class DefaultOperator implements Operator {
    private String name;
    private int operatorId;
    private List<Argument> arguments;
    private int callLineNumber;

    public DefaultOperator(String name, int operatorId, List<Argument> arguments, int callLineNumber) {
        this.name = name;
        this.operatorId = operatorId;
        this.arguments = arguments;
        this.callLineNumber = callLineNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOperatorId() {
        return operatorId;
    }

    @Override
    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public int getCallLineNumber() {
        return callLineNumber;
    }
}
