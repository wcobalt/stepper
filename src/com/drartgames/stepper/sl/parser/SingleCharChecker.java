package com.drartgames.stepper.sl.parser;

public class SingleCharChecker implements TokenChecker {
    private String characters;
    private int tokenType, charactersCount;

    public SingleCharChecker(String characters, int tokenType) {
        this.characters = characters;
        this.tokenType = tokenType;

        charactersCount = characters.length();
    }

    @Override
    public TokenMatch checkMatch(String content, int position, int length) {
        if (length == 1) {
            char ch = content.charAt(position);

            for (int c = 0; c < charactersCount; c++) {
                if (ch == characters.charAt(c))
                    return TokenMatch.FULL_MATCH;
            }
        }

        return TokenMatch.NO_MATCH;
    }

    @Override
    public int getTokenType() {
        return tokenType;
    }
}