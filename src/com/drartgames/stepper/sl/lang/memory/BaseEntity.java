package com.drartgames.stepper.sl.lang.memory;

public class BaseEntity implements Entity {
    private String name;

    public BaseEntity(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
