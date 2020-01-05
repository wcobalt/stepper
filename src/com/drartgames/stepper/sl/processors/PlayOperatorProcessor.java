package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.AudioDescriptor;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.AudioResource;

import java.util.List;

public class PlayOperatorProcessor extends BaseProcessor {
    private static final String LOOP = "loop";
    private static final String ONCE = "once";

    public static final int PLAY_ID = 5;
    public static final int ARGS_COUNT = 2;

    public PlayOperatorProcessor() {
        super(PLAY_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, false, ValueType.STRING_LITERAL, ValueType.GENERAL_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String audioName = arguments.get(0).getValue().getStringValue();
        String loopOnce = arguments.get(1).getValue().getGeneralLiteralValue().toString();

        AudioResource audioResource = lookUpUtil.lookupAudio(interpreter, audioName);

        boolean loop;

        switch (loopOnce) {
            case LOOP:
                loop = true;

                break;
            case ONCE:
                loop = false;

                break;
            default:
                throw new SLRuntimeException("Undefined play modifier: " + loopOnce + " at line " +
                        operator.getCallLineNumber());
        }

        AudioDescriptor audioDescriptor = interpreter.getDisplay().addAudio(audioResource.getAudio(), loop, null);
        audioDescriptor.start();
    }
}
