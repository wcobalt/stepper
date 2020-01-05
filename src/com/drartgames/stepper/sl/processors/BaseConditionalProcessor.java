package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.ConditionalOperator;
import com.drartgames.stepper.sl.lang.Operator;

public abstract class BaseConditionalProcessor extends BaseProcessor {
    public BaseConditionalProcessor(int operatorId) {
        super(operatorId);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        if (!(operator instanceof ConditionalOperator))
            throw new SLRuntimeException("The operator must be a conditional operator");
        else {
            ConditionalOperator conditionalOperator = (ConditionalOperator)operator;

            boolean result = calculateCondition(interpreter, conditionalOperator);

            if (result)
                interpreter.executeOperators(conditionalOperator.getPositiveBranch());
            else
                interpreter.executeOperators(conditionalOperator.getNegativeBranch());
        }
    }

    protected abstract boolean calculateCondition(SLInterpreter interpreter, ConditionalOperator operator) throws SLRuntimeException;
}
