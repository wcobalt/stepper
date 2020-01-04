package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class LoadAudioOperatorProcessor implements OperatorProcessor {
    public static final int LOAD_AUDIO_ID = 2;
    public static final int ARGS_COUNT = 2;

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println(operator.getName());

    }

    @Override
    public int getOperatorId() {
        return LOAD_AUDIO_ID;
    }
}
