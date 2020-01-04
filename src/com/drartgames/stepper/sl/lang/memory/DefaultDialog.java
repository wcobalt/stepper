package com.drartgames.stepper.sl.lang.memory;

import com.drartgames.stepper.sl.lang.Action;

import java.util.regex.Pattern;

public class DefaultDialog extends BaseEntity implements Dialog {
    private Pattern pattern;
    private Action dialogAction;
    private boolean isEnabled;
    private String regexp;

    public DefaultDialog(String name, String regexp, Action dialogAction, boolean isEnabled) {
        super(name);

        pattern = Pattern.compile(regexp);

        this.regexp = regexp;
        this.dialogAction = dialogAction;
        this.isEnabled = isEnabled;
    }

    @Override
    public float matches(String input) {
        return input.matches(regexp) ? 1.0f : 0.0f;
    }

    @Override
    public Action getDialogAction() {
        return dialogAction;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
