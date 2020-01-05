package com.drartgames.stepper.sl.analyzer;

import com.drartgames.stepper.exceptions.AnalysisException;
import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.parser.Parser;

import java.util.List;

public interface ScriptLoader {
    List<Scene> load(String content) throws ParseException, AnalysisException;

    void throwException(String message, Parser parser) throws ParseException, AnalysisException;
}
