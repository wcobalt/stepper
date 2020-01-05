package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.lang.memory.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultLookupUtil implements LookUpUtil {
    private class DefaultLookupResult implements LookupResult {
        Scene scene;
        String entityName;

        public DefaultLookupResult(Scene scene, String entityName) {
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
        DefaultLookupResult result = lookup(interpreter, reference);
        PictureResource resource = result.getScene().getPictureResourceManager().getByName(result.entityName);

        if (resource == null)
            throwException(interpreter, "There's no picture resource with name: " + result.entityName, result.scene);

        return resource;
    }

    @Override
    public AudioResource lookupAudio(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        DefaultLookupResult result = lookup(interpreter, reference);
        AudioResource resource = result.getScene().getAudioResourceManager().getByName(result.entityName);

        if (resource == null)
            throwException(interpreter, "There's no audio resource with name: " + result.entityName, result.scene);

        return resource;
    }

    @Override
    public Action lookupAction(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        DefaultLookupResult result = lookup(interpreter, reference);
        Action action = result.getScene().getActionByName(result.entityName);

        if (action == null)
            throwException(interpreter, "There's no action with name: " + result.entityName, result.scene);

        return action;
    }

    @Override
    public Counter lookupCounter(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        DefaultLookupResult result = lookup(interpreter, reference);
        Counter counter = result.getScene().getCountersManager().getByName(result.entityName);

        if (counter == null)
            throwException(interpreter, "There's no counter with name: " + result.entityName, result.scene);

        return counter;
    }

    @Override
    public Dialog lookupDialog(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        DefaultLookupResult result = lookup(interpreter, reference);
        Dialog dialog = result.getScene().getDialogsManager().getByName(result.entityName);

        if (dialog == null)
            throwException(interpreter, "There's no dialog with name: " + result.entityName, result.scene);

        return dialog;
    }

    @Override
    public Tag lookupTag(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        Tag tag = interpreter.getTagsManager().getByName(reference);

        if (tag == null)
            throwException(interpreter, "There's no tag with name: " + reference, null);

        return tag;
    }

    @Override
    public DefaultLookupResult lookup(SLInterpreter interpreter, String reference) throws SLRuntimeException {
        Matcher matcher = lookupPattern.matcher(reference);

        if (matcher.find()) {
            Scene scene;
            String name;

            String group1 = matcher.group(1), group2 = matcher.group(2);

            if (group2 == null) {
                //current scene
                scene = interpreter.getScenesManager().getCurrentScene();
                name = group1;
            } else {
                //other scene
                scene = interpreter.getScenesManager().getSceneByName(group1);
                name = group2;

                if (scene == null)
                    throwException(interpreter, "There's no scene with name: " + matcher.group(1), null);
            }

            return new DefaultLookupResult(scene, name);
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
