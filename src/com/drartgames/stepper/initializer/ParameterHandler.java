package com.drartgames.stepper.initializer;

public interface ParameterHandler {
    boolean hasArgument();

    void handle(Initializer initializer, String parameter);

    String getParameter();

    String getParameterAlias();

    boolean hasAlias();

    String getHelp();

    String getArgumentHelp();
}
