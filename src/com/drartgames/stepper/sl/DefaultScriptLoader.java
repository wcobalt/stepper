package com.drartgames.stepper.sl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultScriptLoader implements ScriptLoader {
    @Override
    public List<Scene> load(File file) {
        return new ArrayList<>();
    }
}
