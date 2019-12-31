package com.drartgames.stepper.sl.lang;

public class DefaultArgument implements Argument {
    private Value value;

    public DefaultArgument(Value value) {
        this.value = value;
    }

    @Override
    public Value getValue() {
        return value;
    }
}
