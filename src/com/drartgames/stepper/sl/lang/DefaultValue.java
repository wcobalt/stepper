package com.drartgames.stepper.sl.lang;

public class DefaultValue implements Value {
    private ValueType valueType;
    private String stringValue;
    private int integralValue;
    private GeneralLiteral generalLiteralValue;
    private float floatValue;

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
    public GeneralLiteral getGeneralLiteralValue() {
        return generalLiteralValue;
    }
}
