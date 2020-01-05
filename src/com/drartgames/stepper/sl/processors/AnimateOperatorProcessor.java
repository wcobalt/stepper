package com.drartgames.stepper.sl.processors;

import com.drartgames.stepper.display.Animation;
import com.drartgames.stepper.display.AnimationDescriptor;
import com.drartgames.stepper.display.animations.MotionAnimation;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.lang.*;
import com.drartgames.stepper.sl.lang.memory.ShowingImage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimateOperatorProcessor extends BaseProcessor {
    private static final Logger logger = Logger.getLogger(ShowMotionOperatorProcessor.class.getName());

    private static final String V_MOTION = "v_motion";
    private static final String H_MOTION = "h_motion";

    public static final int ANIMATE_ID = 19;
    public static final int ARGS_COUNT = 6;

    public AnimateOperatorProcessor() {
        super(ANIMATE_ID);
    }

    @Override
    public void execute(SLInterpreter interpreter, Operator operator) throws SLRuntimeException {
        checkArgumentsCount(operator, ARGS_COUNT);
        checkArguments(operator, true, ValueType.STRING_LITERAL, ValueType.GENERAL_LITERAL, ValueType.FLOAT_LITERAL,
                ValueType.INTEGRAL_LITERAL, ValueType.BOOL_LITERAL);

        checkArgumentNumber(operator, 5, ValueType.GENERAL_LITERAL, ValueType.NONE_LITERAL);

        List<Argument> arguments = operator.getArguments();

        ShowingImage showingImage = lookUpUtil.lookupShowingImage(interpreter, arguments.get(0).getValue().getStringValue());
        String animationType = arguments.get(1).getValue().getGeneralLiteralValue().toString();
        float optionValue = arguments.get(2).getValue().getFloatValue();
        int duration = arguments.get(3).getValue().getIntegralValue();
        boolean isLooped = arguments.get(4).getValue().getBoolValue();

        //@todo callback getting to mathod
        Value callbackValue = arguments.get(5).getValue();
        ValueType callbackValueType = callbackValue.getValueType();

        Action action = (callbackValueType != ValueType.NONE_LITERAL
                ? lookUpUtil.lookupAction(interpreter, callbackValue.getGeneralLiteralValue().toString())
                : null);

        Animation animation = null;

        switch (animationType) {
            case V_MOTION:
                animation = new MotionAnimation(duration, optionValue, true);

                break;
            case H_MOTION:
                animation = new MotionAnimation(duration, optionValue, false);

                break;
            default:
                throw new SLRuntimeException("Undefined type of animation: " + animationType);
        }

        //erasing of current animation is absolutely undefined behaviour
        AnimationDescriptor animationDescriptor = interpreter.getDisplay().addAnimation(showingImage.getImage(),
                animation, isLooped, (descriptor) -> {
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
