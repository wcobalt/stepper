package com.drartgames.stepper;

import com.drartgames.stepper.initializer.DefaultStepperInitializer;
import com.drartgames.stepper.initializer.Initializer;

public class Launcher {
    private static Version version;

    public static void main(String... args) {
        version = new StepperVersion(1, 0, 0, 0);

        Initializer initializer = new DefaultStepperInitializer();
        initializer.initialize(args);
        initializer.run();
    }

    public static Version getVersion() {
        return version;
    }
}
