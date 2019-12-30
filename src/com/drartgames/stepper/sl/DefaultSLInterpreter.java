package com.drartgames.stepper.sl;

import com.drartgames.stepper.DefaultVersion;
import com.drartgames.stepper.Version;

public class DefaultSLInterpreter implements SLInterpreter {
    private Version version;

    public DefaultSLInterpreter() {
        version = new DefaultVersion("1.0.0:0");
    }

    @Override
    public Version getSLVersion() {
        return version;
    }
}
