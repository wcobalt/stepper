package com.drartgames.stepper.sl.lang.memory;

public interface Counter extends Entity {
    int getCounter();

    void setCounter(int counter);

    void add(int number);
}
