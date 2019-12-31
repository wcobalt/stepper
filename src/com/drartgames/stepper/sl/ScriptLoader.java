package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.ParseException;

import java.util.List;

public interface ScriptLoader {
    List<Scene> load(String content) throws ParseException;
}
