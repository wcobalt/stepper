package com.drartgames.stepper.sl.lang;

import java.util.List;

public interface ConditionalOperator extends Operator {
    List<Operator> getPositiveBranch();

    List<Operator> getNegativeBranch();
}
