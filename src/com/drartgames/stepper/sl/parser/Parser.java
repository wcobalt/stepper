package com.drartgames.stepper.sl.parser;

import com.drartgames.stepper.exceptions.ParseException;

public interface Parser {
    void parse(String content) throws ParseException;

    boolean next() throws ParseException;

    String getTokenValue();

    int getTokenType();

    int getCurrentLineNumber();
}
