package com.drartgames.stepper.sl.parser;

import com.drartgames.stepper.exceptions.ParseException;

import java.util.List;

public class DefaultTokenizer implements Tokenizer {
    private List<TokenChecker> tokenCheckers;
    private String currentTokenValue;
    private String text;
    private int currentLineNumber, currentPosition;
    private int currentTokenType;

    public DefaultTokenizer(List<TokenChecker> tokenCheckers) {
        this.tokenCheckers = tokenCheckers;
    }

    @Override
    public void tokenize(String content) throws ParseException {
        if (!content.isEmpty()) {
            currentLineNumber = 1;
            currentPosition = 0;

            text = content;
        } else
            throw new IllegalArgumentException("Content to be parsed must be not empty");
    }

    @Override
    public boolean next() throws ParseException {
        return false;
    }

    @Override
    public String getTokenValue() {
        return currentTokenValue;
    }

    @Override
    public int getTokenType() {
        return currentTokenType;
    }

    @Override
    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void setCurrentPosition(int position) {
        currentPosition = position;
    }
}
