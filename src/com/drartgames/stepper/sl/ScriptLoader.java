package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.Scene;

import java.util.List;

public interface ScriptLoader {
    List<Scene> load(String content) throws ParseException;
}
