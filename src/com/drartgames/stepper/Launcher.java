package com.drartgames.stepper;

import com.drartgames.stepper.initializer.DefaultStepperInitializer;
import com.drartgames.stepper.initializer.Initializer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {
    private static Version version;
    private static Logger logger = Logger.getLogger(Launcher.class.getName());

    public static void main(String... args) {
        version = new DefaultVersion("1.0.0:0");

        try {
            Initializer initializer = new DefaultStepperInitializer();
            initializer.initialize(args);
            initializer.run();
        } catch (SLVersionMismatchException exc) {
            logger.log(Level.SEVERE, "Unsupported SL version", exc);
        }
    }

    public static Version getVersion() {
        return version;
    }
}
