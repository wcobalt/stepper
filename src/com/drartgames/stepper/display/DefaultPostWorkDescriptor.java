package com.drartgames.stepper.display;

public class DefaultPostWorkDescriptor extends BaseDescriptor implements PostWorkDescriptor {
    private PostWork work;

    public DefaultPostWorkDescriptor(Display display, PostWork work) {
        super(display);
        this.work = work;
    }

    @Override
    public PostWork getWork() {
        return work;
    }
}
