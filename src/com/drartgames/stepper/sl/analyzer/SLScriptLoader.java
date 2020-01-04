package com.drartgames.stepper.sl.analyzer;

import com.drartgames.stepper.exceptions.AnalysisException;
import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.parser.Parser;

import java.util.List;

public interface SLScriptLoader extends ScriptLoader {
    List<Operator> analyzeStatement(Parser parser) throws ParseException, AnalysisException;
}
