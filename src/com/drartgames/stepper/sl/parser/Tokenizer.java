package com.drartgames.stepper.sl.parser;

import com.drartgames.stepper.exceptions.ParseException;

public interface Tokenizer {
    void tokenize(String content) throws ParseException;

    boolean next() throws ParseException;

    String getTokenValue();

    int getCurrentLineNumber();

    int getCurrentPosition();

    int getTokenType();

    void setCurrentPosition(int position);

    String getText();
}
