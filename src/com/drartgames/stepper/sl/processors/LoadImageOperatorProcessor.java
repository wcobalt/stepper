package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.DefaultPicture;
import com.drartgames.stepper.display.Picture;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.Argument;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.ValueType;
import com.drartgames.stepper.sl.lang.memory.DefaultPictureResource;
import com.drartgames.stepper.sl.lang.memory.PictureResource;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadImageOperatorProcessor extends BaseProcessor implements OperatorProcessor {
    private static final Logger logger = Logger.getLogger(LoadImageOperatorProcessor.class.getName());

    public static final int LOAD_IMAGE_ID = 1;
    public static final int ARGS_COUNT = 2;

    private static final String GRAPHICS_PREFIX = "graphics";

    public LoadImageOperatorProcessor() {
        super(LOAD_IMAGE_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArguments(operator, ValueType.STRING_LITERAL, ValueType.STRING_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String name = arguments.get(0).getValue().getStringValue();
        String path = arguments.get(1).getValue().getStringValue();

        String pathPrefix = interpreter.getDisplay().getInitializer().getQuestDirectory().getAbsolutePath();

        try {
            Picture picture = new DefaultPicture(pathPrefix + "/" + GRAPHICS_PREFIX + "/" + path);
            PictureResource pictureResource = new DefaultPictureResource(name, picture);

            interpreter.getScenesManager().getCurrentScene().getPictureResourceManager().add(pictureResource);
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Unable to load picture at line " + operator.getCallLineNumber() +
                    " , IOException has occurred", exc);
        }
    }
}
