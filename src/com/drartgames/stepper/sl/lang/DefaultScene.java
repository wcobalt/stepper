package com.drartgames.stepper.sl.lang;

import java.util.List;

public class DefaultScene implements Scene {
    private String name;
    private List<Action> actions;

    public DefaultScene(String name, List<Action> actions) {
        this.name = name;
        this.actions = actions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }
}
