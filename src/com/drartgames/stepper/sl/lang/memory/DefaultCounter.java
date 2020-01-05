package com.drartgames.stepper.sl.lang.memory;

public class DefaultCounter extends BaseEntity implements Counter {
    private int counter;

    public DefaultCounter(String name) {
        super(name);
    }

    public DefaultCounter(String name, int counter) {
        super(name);
        this.counter = counter;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void add(int number) {
        this.counter += number;
    }
}
