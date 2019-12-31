package com.drartgames.stepper.sl.parser;

import com.drartgames.stepper.exceptions.ParseException;

import java.util.List;

public class DefaultTokenizer implements Tokenizer {
    private final static char NEWLINE = '\n';
    private List<TokenChecker> tokenCheckers;
    private String currentTokenValue;
    private String text;
    private int textSize;
    private int currentLineNumber, currentPosition;
    private int currentTokenType;

    public DefaultTokenizer(List<TokenChecker> tokenCheckers) {
        if (tokenCheckers.size() == 0)
            throw new IllegalArgumentException("Token checkers list must contain at least one token checker");

        this.tokenCheckers = tokenCheckers;
    }

    @Override
    public void tokenize(String content) throws ParseException {
        if (!content.isEmpty()) {
            currentLineNumber = 1;
            currentPosition = 0;

            text = content;
            textSize = content.length();
        } else
            throw new IllegalArgumentException("Content to be parsed must be not empty");
    }

    @Override
    public boolean next() throws ParseException {
        int checkersCount = tokenCheckers.size();
        boolean[] doNotUse = new boolean[checkersCount];

        int lastFullMatchPosition = -1;
        int lastFullMatchChecker = -1;
        int totalNewlines = 0, lastFullNewlines = 0;

        for (int p = currentPosition; p < textSize; p++) {
            if (text.charAt(p) == NEWLINE) totalNewlines++;

            boolean hasAnyMatches = false;

            for (int c = 0; c < checkersCount; c++) {
                if (!doNotUse[c]) {
                    hasAnyMatches = true;

                    TokenMatch match = tokenCheckers.get(c).checkMatch(text, currentPosition, p - currentPosition + 1);

                    switch (match) {
                        case FULL_MATCH:
                            lastFullMatchPosition = p;
                            lastFullMatchChecker = c;
                            lastFullNewlines = totalNewlines;

                            break;

                        case PROBABLE_MATCH:
                            //everything's fine, just do nothing

                            break;

                        case NO_MATCH:
                            doNotUse[c] = true;

                            break;
                    }
                }
            }

            //newlines

            //if there're either no matches or it's the last char
            if (!hasAnyMatches || p == textSize - 1) {
                if (lastFullMatchChecker != -1) {
                    currentTokenValue = text.substring(currentPosition, lastFullMatchPosition + 1);
                    currentPosition = lastFullMatchPosition + 1;//may be after last char, but it's not critical
                    currentTokenType = tokenCheckers.get(lastFullMatchChecker).getTokenType();
                    currentLineNumber += lastFullNewlines;

                    return true;
                } else
                    throw new ParseException("Undefined token " + text.charAt(currentPosition) + " at line "
                            + getCurrentLineNumber());
            }
        }

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

    @Override
    public String getText() {
        return text;
    }
}
