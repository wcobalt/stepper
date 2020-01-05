package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.Audio;
import com.drartgames.stepper.display.DefaultAudio;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.AudioResource;
import com.drartgames.stepper.sl.lang.memory.DefaultAudioResource;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadAudioOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    private static final Logger logger = Logger.getLogger(LoadImageOperatorProcessor.class.getName());

    public static final int LOAD_AUDIO_ID = 2;
    public static final int ARGS_COUNT = 2;

    private static final String AUDIO_PREFIX = "audio";

    public LoadAudioOperatorProcessor() {
        super(LOAD_AUDIO_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL, ValueType.STRING_LITERAL);

        //@todo NEVER DOUBLE ANY CODE
        List<Argument> arguments = operator.getArguments();

        String name = arguments.get(0).getValue().getStringValue();
        String path = arguments.get(1).getValue().getStringValue();

        String pathPrefix = interpreter.getDisplay().getInitializer().getQuestDirectory().getAbsolutePath();

        try {
            Audio audio = new DefaultAudio(pathPrefix + "/" + AUDIO_PREFIX + "/" + path);
            AudioResource audioResource = new DefaultAudioResource(name, audio);

            interpreter.getScenesManager().getCurrentScene().getAudioResourceManager().add(audioResource);
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Unable to load picture at line " + operator.getCallLineNumber() +
                    ", IOException has occurred", exc);
        } catch (UnsupportedAudioFileException exc) {
            logger.log(Level.SEVERE, "Unable to load audio at line " + operator.getCallLineNumber() +
                    " because of unsupported format", exc);
        }
    }
}
