package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.Picture;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.*;
import com.drartgames.stepper.sl.lang.memory.PictureResource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowOperatorProcessor extends BaseProcessor {
    private static final Logger logger = Logger.getLogger(ShowOperatorProcessor.class.getName());

    public static final int SHOW_ID = 8;
    public static final int ARGS_COUNT = 3;

    public ShowOperatorProcessor() {
        super(SHOW_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArgumentsCount(operator, ARGS_COUNT);
        checkArgumentNumber(operator, 0, ValueType.STRING_LITERAL);
        checkArgumentNumber(operator, 1, ValueType.STRING_LITERAL, ValueType.NONE_LITERAL);
        checkArgumentNumber(operator, 2, ValueType.GENERAL_LITERAL, ValueType.NONE_LITERAL);

        List<Argument> arguments = operator.getArguments();

        //message
        String message = arguments.get(0).getValue().getStringValue();

        PictureResource pictureResource = null;
        Value pictureValue = arguments.get(1).getValue();

        if (pictureValue.getValueType() != ValueType.NONE_LITERAL) {
            pictureResource = lookUpUtil.lookupPicture(interpreter, pictureValue.getStringValue());
        }

        Picture picture = pictureResource != null ? pictureResource.getPicture() : null;

        //action
        Action action = null;
        Value actionValue = arguments.get(2).getValue();

        if (actionValue.getValueType() != ValueType.NONE_LITERAL) {
            action = lookUpUtil.lookupAction(interpreter, actionValue.getGeneralLiteralValue().toString());
        }

        Action finalAction = action;

        interpreter.getToolkit().showMessage(message, picture, () -> {
            if (finalAction != null) {
                try {
                    interpreter.executeAction(finalAction);
                } catch (SLRuntimeException exc) {
                    logger.log(Level.SEVERE, "Unable to execute show callback called at line " +
                            operator.getCallLineNumber(), exc);
                }
            }
        });
    }
}
