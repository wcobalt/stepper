package com.drartgames.stepper.sl.lang;

public interface Value {
    ValueType getValueType();

    String getStringValue();

    int getIntegralValue();

    float getFloatValue();

    GeneralLiteral getGeneralLiteralValue();
}
