package com.drartgames.stepper.sl.lang;

public class DefaultValue implements Value {
    private ValueType valueType;
    private String stringValue;
    private int integralValue;
    private GeneralLiteral generalLiteralValue;
    private float floatValue;
    private boolean boolValue;

    public DefaultValue() {
        valueType = ValueType.NONE_LITERAL;
    }

    public DefaultValue(String stringValue) {
        valueType = ValueType.STRING_LITERAL;

        this.stringValue = stringValue;
    }

    public DefaultValue(int integralValue) {
        valueType = ValueType.INTEGRAL_LITERAL;

        this.integralValue = integralValue;
    }

    public DefaultValue(GeneralLiteral generalLiteralValue) {
        valueType = ValueType.GENERAL_LITERAL;

        this.generalLiteralValue = generalLiteralValue;
    }

    public DefaultValue(float floatValue) {
        valueType = ValueType.FLOAT_LITERAL;

        this.floatValue = floatValue;
    }

    public DefaultValue(boolean boolValue) {
        valueType = ValueType.BOOL_LITERAL;

        this.boolValue = boolValue;
    }

    @Override
    public float getFloatValue() {
        return floatValue;
    }

    @Override
    public ValueType getValueType() {
        return valueType;
    }

    @Override
    public String getStringValue() {
        return stringValue;
    }

    @Override
    public int getIntegralValue() {
        return integralValue;
    }

    @Override
    public boolean getBoolValue() {
        return boolValue;
    }

    @Override
    public boolean isNoneValue() {
        return valueType == ValueType.NONE_LITERAL;
    }

    @Override
    public GeneralLiteral getGeneralLiteralValue() {
        return generalLiteralValue;
    }
}
