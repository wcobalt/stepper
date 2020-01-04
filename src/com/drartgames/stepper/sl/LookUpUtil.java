package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.memory.*;

public interface LookUpUtil {
    PictureResource lookupPicture(SLInterpreter interpreter, String reference) throws SLRuntimeException;

    AudioResource lookupAudio(SLInterpreter interpreter, String reference) throws SLRuntimeException;

    Action lookupAction(SLInterpreter interpreter, String reference) throws SLRuntimeException;

    Counter lookupCounter(SLInterpreter interpreter, String reference) throws SLRuntimeException;

    Dialog lookupDialog(SLInterpreter interpreter, String reference) throws SLRuntimeException;

}
