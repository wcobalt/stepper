package com.drartgames.stepper.sl.lang;

public interface Value {
    ValueType getValueType();

    String getStringValue();

    int getIntegralValue();

    float getFloatValue();

    boolean getBoolValue();

    boolean isNoneValue();

    GeneralLiteral getGeneralLiteralValue();
}
