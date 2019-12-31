package com.drartgames.stepper.sl;

import java.io.File;
import java.util.List;

public interface ScriptLoader {
    List<Scene> load(File file);
}
