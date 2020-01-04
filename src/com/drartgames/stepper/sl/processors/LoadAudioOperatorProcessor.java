package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class LoadAudioOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    public static final int LOAD_AUDIO_ID = 2;
    public static final int ARGS_COUNT = 2;

    public LoadAudioOperatorProcessor() {
        super(LOAD_AUDIO_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        System.out.println("Loading an audio: " + operator.getName());
    }
}
