package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.AnalysisException;
import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.parser.Parser;

public interface StatementAnalyzer {
    Operator analyze(Parser parser, ScriptLoader scriptLoader) throws ParseException, AnalysisException;

    String getStatementBegin();
}
