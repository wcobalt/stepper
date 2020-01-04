package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.lang.memory.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultLookupUtil implements LookUpUtil {
    private class LookupResult {
        Scene scene;
        String entityName;

        public LookupResult(Scene scene, String entityName) {
            this.scene = scene;
            this.entityName = entityName;
        }

        public Scene getScene() {
            return scene;
        }

        public String getEntityName() {
            return entityName;
        }
    }

    Pattern lookupPattern;

    public DefaultLookupUtil() {
        lookupPattern = Pattern.compile("([a-zA-Z0-9_]+)(?:\\.([a-zA-Z0-9_]+))?");
    }

    @Override
    public PictureResource lookupPicture(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        LookupResult result = lookup(interpreter, reference);
        PictureResource resource = result.getScene().getPictureResourceManager().getByName(result.entityName);

        if (resource == null)
            throwException(interpreter, "There's no picture resource with name: " + result.entityName, result.scene);

        return resource;
    }

    @Override
    public AudioResource lookupAudio(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        LookupResult result = lookup(interpreter, reference);
        AudioResource resource = result.getScene().getAudioResourceManager().getByName(result.entityName);

        if (resource == null)
            throwException(interpreter, "There's no audio resource with name: " + result.entityName, result.scene);

        return resource;
    }

    @Override
    public Action lookupAction(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        LookupResult result = lookup(interpreter, reference);
        Action action = result.getScene().getActionByName(result.entityName);

        if (action == null)
            throwException(interpreter, "There's no action with name: " + result.entityName, result.scene);

        return action;
    }

    @Override
    public Counter lookupCounter(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        LookupResult result = lookup(interpreter, reference);
        Counter counter = result.getScene().getCountersManager().getByName(result.entityName);

        if (counter == null)
            throwException(interpreter, "There's no counter with name: " + result.entityName, result.scene);

        return counter;
    }

    @Override
    public Dialog lookupDialog(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        LookupResult result = lookup(interpreter, reference);
        Dialog dialog = result.getScene().getDialogsManager().getByName(result.entityName);

        if (dialog == null)
            throwException(interpreter, "There's no dialog with name: " + result.entityName, result.scene);

        return dialog;
    }

    private LookupResult lookup(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        Matcher matcher = lookupPattern.matcher(reference);

        if (matcher.find()) {
            int groupCount = matcher.groupCount();

            Scene scene;
            String name;

            switch (groupCount) {
                case 1:
                    scene = interpreter.getScenesManager().getCurrentScene();
                    name = matcher.group(1);

                    break;

                case 2:
                    scene = interpreter.getScenesManager().getSceneByName(matcher.group(1));
                    name = matcher.group(2);

                    if (scene == null)
                        throwException(interpreter, "Failed lookup. There's no scene with name: " + matcher.group(1), null);

                    break;
                default:
                    throw new SLRuntimeException("Some totally unexpected shit happened");
            }

            return new LookupResult(scene, name);
        } else
            throw new SLRuntimeException("Unable to perform lookup. Undefined syntax of the reference: " + reference);
    }

    private void throwException(SLInterpreter interpreter, String message, Scene scene) throws SLRuntimeException {
        //@todo add such thing everywhere also with scene name and file and other stuff
        throw new SLRuntimeException("Failed lookup. " + message +
                (scene != null ? " in scene \"" + scene.getName() + "\"" : "") + " at line " +
                interpreter.getCurrentOperator().getCallLineNumber());
    }
}
