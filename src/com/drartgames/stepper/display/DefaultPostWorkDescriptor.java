package com.drartgames.stepper.display;

public class DefaultPostWorkDescriptor extends BaseDescriptor implements PostWorkDescriptor {
    private DescriptorWork work;

    public DefaultPostWorkDescriptor(Display display, DescriptorWork work) {
        super(display);
        this.work = work;
    }

    @Override
    public void execute() {
        work.execute(this);
    }

    @Override
    public DescriptorWork getWork() {
        return work;
    }
}
