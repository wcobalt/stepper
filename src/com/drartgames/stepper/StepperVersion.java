package com.drartgames.stepper;

public class StepperVersion extends DefaultVersion {
    public StepperVersion(int major, int minor, int index, int buildNumber) {
        super(major, minor, index, buildNumber);
    }

    @Override
    public String getFullStringVersion() {
        return "Stepper v" + getStringVersion();
    }
}
