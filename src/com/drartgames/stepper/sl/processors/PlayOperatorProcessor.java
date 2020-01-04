package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class PlayOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int PLAY_ID = 5;
    public static final int ARGS_COUNT = 2;

    public PlayOperatorProcessor() {
        super(PLAY_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println("Playing audio: " + operator.getName());
    }
}
