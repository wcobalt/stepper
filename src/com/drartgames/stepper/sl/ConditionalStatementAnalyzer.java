package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.AnalysisException;
import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.DefaultConditionalOperator;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class ConditionalStatementAnalyzer extends SingleStatementAnalyzer implements StatementAnalyzer {
    private String negativeBranchKeyword;

    public ConditionalStatementAnalyzer(String operatorName, int operatorId, int argumentCount, String negativeBranchKeyword) {
        super(operatorName, operatorId, argumentCount);

        this.negativeBranchKeyword = negativeBranchKeyword;
    }

    @Override
    public Operator analyze(Parser parser, ScriptLoader scriptLoader) throws ParseException, AnalysisException {
        if (scriptLoader instanceof SLScriptLoader) {
            SLScriptLoader slScriptLoader = (SLScriptLoader)scriptLoader;

            Operator condition = super.analyze(parser, scriptLoader);
            List<Operator> positiveBranch = slScriptLoader.analyzeStatement(parser);
            List<Operator> negativeBranch = null;

            if (parser.getTokenValue().equals(negativeBranchKeyword)) {
                if (parser.next()) {
                    negativeBranch = slScriptLoader.analyzeStatement(parser);
                } else
                    scriptLoader.throwException("Statement has been expected in negative branch", parser);
            } else
                negativeBranch = new ArrayList<>();

            return new DefaultConditionalOperator(condition.getName(), condition.getOperatorId(), condition.getArguments(),
                    condition.getCallLineNumber(), positiveBranch, negativeBranch);
        } else
            throw new IllegalArgumentException("This statement analyzer supports only SLScriptLoader script loaders");
    }
}
