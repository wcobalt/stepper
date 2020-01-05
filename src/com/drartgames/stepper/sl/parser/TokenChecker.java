package com.drartgames.stepper.sl.parser;

public interface TokenChecker {
    TokenMatch checkMatch(String content, int position, int length);

    int getTokenType();
}
