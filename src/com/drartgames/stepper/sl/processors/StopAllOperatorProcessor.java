package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.AudioDescriptor;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Operator;

public class StopAllOperatorProcessor extends BaseProcessor {
    public static final int STOP_ALL_ID = 20;
    public static final int ARGS_COUNT = 0;

    public StopAllOperatorProcessor() {
        super(STOP_ALL_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        for (AudioDescriptor descriptor : interpreter.getDisplay().getDisplayState().getAudioDescriptors()) {
            descriptor.stop();
            descriptor.setDoFree(true);
        }
    }
}
