package com.drartgames.stepper.sl.lang;

public class DefaultGeneralLiteral implements GeneralLiteral {
    private String stringRepresentation;

    public DefaultGeneralLiteral(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
