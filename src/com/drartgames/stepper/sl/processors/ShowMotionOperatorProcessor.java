package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.Animation;
import com.drartgames.stepper.display.AnimationDescriptor;
import com.drartgames.stepper.display.ImageDescriptor;
import com.drartgames.stepper.display.animations.MotionToPointAnimation;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.LookupResult;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.*;
import com.drartgames.stepper.sl.lang.memory.DefaultShowingImage;
import com.drartgames.stepper.sl.lang.memory.PictureResource;
import com.drartgames.stepper.sl.lang.memory.ShowingImage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowMotionOperatorProcessor extends BaseProcessor {
    private static final Logger logger = Logger.getLogger(ShowMotionOperatorProcessor.class.getName());

    public static final int SHOW_MOTION_ID = 18;
    public static final int ARGS_COUNT = 9;

    public ShowMotionOperatorProcessor() {
        super(SHOW_MOTION_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArgumentsCount(operator, ARGS_COUNT);
        checkArguments(operator, true, ValueType.STRING_LITERAL, ValueType.STRING_LITERAL, ValueType.FLOAT_LITERAL,
                ValueType.FLOAT_LITERAL, ValueType.FLOAT_LITERAL, ValueType.FLOAT_LITERAL, ValueType.FLOAT_LITERAL,
                ValueType.INTEGRAL_LITERAL);
        checkArgumentNumber(operator, 8, ValueType.GENERAL_LITERAL, ValueType.NONE_LITERAL);

        List<Argument> arguments = operator.getArguments();

        String showingImageName = arguments.get(0).getValue().getStringValue();

        LookupResult lookupResult = lookUpUtil.lookup(interpreter, showingImageName);
        ShowingImage showingImage = lookupResult.getScene().getShowingImageManager().getByName(lookupResult.getEntityName());

        float x0 = arguments.get(3).getValue().getFloatValue();
        float y0 = arguments.get(4).getValue().getFloatValue();
        float x1 = arguments.get(5).getValue().getFloatValue();
        float y1 = arguments.get(6).getValue().getFloatValue();

        int duration = arguments.get(7).getValue().getIntegralValue();

        Value callbackValue = arguments.get(8).getValue();
        ValueType callbackValueType = callbackValue.getValueType();

        Action action = (callbackValueType != ValueType.NONE_LITERAL
                ? lookUpUtil.lookupAction(interpreter, callbackValue.getGeneralLiteralValue().toString())
                : null);

        ImageDescriptor imageDescriptor;

        if (showingImage == null) {
            PictureResource pictureResource = lookUpUtil.lookupPicture(interpreter, arguments.get(1).getValue().getStringValue());

            //@todo numbers of arguments to consts
            float width = arguments.get(2).getValue().getFloatValue();

            imageDescriptor = interpreter.getDisplay().addPicture(pictureResource.getPicture(),
                    width, x0, y0);

            showingImage = new DefaultShowingImage(showingImageName, imageDescriptor, null);
            interpreter.getScenesManager().getCurrentScene().getShowingImageManager().add(showingImage);
        } else {
            imageDescriptor = showingImage.getImage();

            imageDescriptor.setX(x0);
            imageDescriptor.setY(y0);

            showingImage.getCurrentAnimation().setDoFree(true);//@fixme it will continue to move for one more tick
        }

        Animation animation = new MotionToPointAnimation(duration, x1, y1);
        animation.setInitPos(imageDescriptor);
        AnimationDescriptor animationDescriptor = interpreter.getDisplay().addAnimation(imageDescriptor, animation,
                false, (descriptor) -> {
                    //@todo to method
                    if (action != null) {
                        try {
                            interpreter.executeAction(action);
                        } catch (SLRuntimeException exc) {
                            logger.log(Level.SEVERE, "Unable to execute show_motion callback at line " +
                                    operator.getCallLineNumber(), exc);
                        }
                    }
                });

        showingImage.setCurrentAnimation(animationDescriptor);
    }
}
