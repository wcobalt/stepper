package com.drartgames.stepper.sl.lang;

import java.util.List;

public class DefaultAction implements Action {
    private String name;
    private List<Operator> operators;

    public DefaultAction(String name, List<Operator> operators) {
        this.name = name;
        this.operators = operators;
    }

    @Override
    public List<Operator> getOperators() {
        return operators;
    }

    @Override
    public String getName() {
        return name;
    }
}
