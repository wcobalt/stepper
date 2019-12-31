package com.drartgames.stepper.sl.parser;

import com.drartgames.stepper.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SLParser implements Parser {
    private Tokenizer tokenizer;

    public SLParser() {
        List<TokenChecker> checkers = new ArrayList<>();

        tokenizer = new DefaultTokenizer(checkers);
    }

    @Override
    public void parse(String content) throws ParseException {
        tokenizer.tokenize(content);
    }

    @Override
    public boolean next() throws ParseException {
        return false;
    }

    @Override
    public String getTokenValue() {
        return tokenizer.getTokenValue();
    }

    @Override
    public int getTokenType() {
        return tokenizer.getTokenType();
    }

    @Override
    public int getCurrentLineNumber() {
        return tokenizer.getCurrentLineNumber();
    }
}
