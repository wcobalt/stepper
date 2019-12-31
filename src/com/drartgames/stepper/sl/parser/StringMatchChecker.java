package com.drartgames.stepper.sl.parser;

public class StringMatchChecker implements TokenChecker {
    private String string;
    private int tokenType, stringLength;

    public StringMatchChecker(String string, int tokenType) {
        this.string = string;
        this.tokenType = tokenType;

        stringLength = string.length();
    }
    @Override
    public TokenMatch checkMatch(String content, int position, int length) {
        if (length > stringLength) return TokenMatch.NO_MATCH;
        else {
            //check all needed chars of pattern string
            for (int i = 0; i < length; i++) {
                if (string.charAt(i) != content.charAt(position + i))
                    return TokenMatch.NO_MATCH;
            }

            //current part matches pattern string
            //if lengths are equal then full match
            //else - partial
            if (length == stringLength)
                return TokenMatch.FULL_MATCH;
            else
                return TokenMatch.PROBABLE_MATCH;
        }
    }

    @Override
    public int getTokenType() {
        return tokenType;
    }
}
