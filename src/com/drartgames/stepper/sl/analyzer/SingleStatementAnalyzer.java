package com.drartgames.stepper.sl.analyzer;

import com.drartgames.stepper.exceptions.AnalysisException;
import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.*;
import com.drartgames.stepper.sl.parser.Parser;
import com.drartgames.stepper.sl.parser.SLParser;

import java.util.ArrayList;
import java.util.List;

public class SingleStatementAnalyzer implements StatementAnalyzer {
    private static final char QUOTES = '"';
    private static final char N = 'n';
    private static final char BACKSLASH = '\\';
    private static final char NEWLINE = '\n';

    private int argumentCount;
    private int operatorId;
    private String operatorName;

    public SingleStatementAnalyzer(String operatorName, int operatorId, int argumentCount) {
        this.argumentCount = argumentCount;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
    }

    @Override
    public Operator analyze(Parser parser, ScriptLoader scriptLoader) throws ParseException, AnalysisException {
        List<Argument> arguments = new ArrayList<>();
        int callLineNumber = parser.getCurrentLineNumber();

        for (int i = 0; i < argumentCount; i++) {
            if (parser.next()) {
                arguments.add(analyzeArgument(parser, scriptLoader));
            } else
                scriptLoader.throwException("Another argument has been expected, in " + operatorName + " call", parser);
        }

        if (!parser.next())
            scriptLoader.throwException("Either block's end or operator have been expected", parser);

        return new DefaultOperator(operatorName, operatorId, arguments, callLineNumber);
    }

    private Argument analyzeArgument(Parser parser, ScriptLoader scriptLoader) {
        Value value = null;
        String token = parser.getTokenValue();

        switch (parser.getTokenType()) {
            case SLParser.STRING:
                value = new DefaultValue(processString(token));

                break;
            case SLParser.INTEGRAL:
                value = new DefaultValue(Integer.valueOf(token));

                break;
            case SLParser.KEYWORD_ID:
                value = new DefaultValue(new DefaultGeneralLiteral(token));

                break;
            case SLParser.FLOAT:
                value = new DefaultValue(Float.valueOf(token));

                break;
        }

        return new DefaultArgument(value);
    }

    @Override
    public String getStatementBegin() {
        return operatorName;
    }

    private String processString(String string) {
        String str = string.replaceAll("\n", "");
        String result = "";

        int stringSize = str.length();

        int slashesCount = 0;

        for (int i = 1; i < stringSize - 1; i++) {
            char ch = str.charAt(i);

            switch (ch) {
                case QUOTES:
                    //string is valid so before every " n%2==1 backslashes stand
                    result += QUOTES;

                    break;
                case N:
                    if (slashesCount % 2 == 1) result += NEWLINE;
                    else result += N;

                    break;
                case BACKSLASH:
                    if (slashesCount % 2 == 1) result += BACKSLASH;

                    slashesCount++;

                    break;
                default:
                    result += ch;
            }

            if (ch != BACKSLASH) slashesCount = 0;
        }

        return result.replaceAll(" +", " ");
    }
}
