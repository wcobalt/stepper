package com.drartgames.stepper.sl.lang;

import java.util.List;

public class DefaultConditionalOperator extends DefaultOperator implements ConditionalOperator {
    private List<Operator> positiveBranch, negativeBranch;

    public DefaultConditionalOperator(String name, int operatorId, List<Argument> arguments, int callLineNumber,
                                      List<Operator> positiveBranch, List<Operator> negativeBranch) {
        super(name, operatorId, arguments, callLineNumber);

        this.positiveBranch = positiveBranch;
        this.negativeBranch = negativeBranch;
    }

    @Override
    public List<Operator> getPositiveBranch() {
        return positiveBranch;
    }

    @Override
    public List<Operator> getNegativeBranch() {
        return negativeBranch;
    }
}
