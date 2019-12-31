package com.drartgames.stepper.sl.parser;

import com.drartgames.stepper.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

public class SLParser implements Parser {
    public static final int KEYWORD_ID = 1,
            SPACE = 2,
            DELIMITER = 3,
            NUMBER = 4,
            STRING = 5,
            SINGLE_LINE_COMMENT = 6;

    private static final String DELIMITERS = "{}";
    private static final String SINGLE_LINE_COMMENT_SEQUENCE = "//";
    private static final String SPACES = "\n\t ";

    //is declared in several places - that's no good
    private static final char NEWLINE = '\n';

    private Tokenizer tokenizer;

    private class KeywordIdChecker implements TokenChecker {
        @Override
        public TokenMatch checkMatch(String content, int position, int length) {
            String keyword = content.substring(position, position + length);

            if (keyword.matches("^[a-zA-Z_][0-9A-Za-z_]*$"))
                return TokenMatch.FULL_MATCH;
            else
                return TokenMatch.NO_MATCH;
        }

        @Override
        public int getTokenType() {
            return KEYWORD_ID;
        }
    }

    private class StringChecker implements TokenChecker {
        private static final char BACKSLASH = '\\';
        private static final char QUOTES = '"';

        @Override
        public TokenMatch checkMatch(String content, int position, int length) {
            int slashesCount = 0;

            for (int i = 0; i < length; i++) {
                char ch = content.charAt(position + i);

                if (i == 0) {
                    if (ch != QUOTES) return TokenMatch.NO_MATCH;
                } else {
                    switch (ch) {
                        case BACKSLASH:
                            slashesCount++;

                            break;
                        case QUOTES:
                            //end of string
                            if (slashesCount % 2 == 0) {
                                if (i == length - 1)
                                    return TokenMatch.FULL_MATCH;
                                else
                                    return TokenMatch.NO_MATCH;
                            }

                            slashesCount = 0;

                            break;
                        default:
                            slashesCount = 0;
                    }
                }
            }

            return TokenMatch.PROBABLE_MATCH;
        }

        @Override
        public int getTokenType() {
            return STRING;
        }
    }

    private class NumberChecker implements TokenChecker {
        private static final char MINUS = '-';

        @Override
        public TokenMatch checkMatch(String content, int position, int length) {
            TokenMatch tokenMatch = TokenMatch.NO_MATCH;

            for (int i = 0; i < length; i++) {
                char ch = content.charAt(position + i);

                if (i == 0 && ch == MINUS) {
                    tokenMatch = TokenMatch.PROBABLE_MATCH;
                } else if (ch >= '0' && ch <= '9') {
                    tokenMatch = TokenMatch.FULL_MATCH;
                } else
                    return TokenMatch.NO_MATCH;
            }

            return tokenMatch;
        }

        @Override
        public int getTokenType() {
            return NUMBER;
        }
    }

    public SLParser() {
        List<TokenChecker> checkers = new ArrayList<>();

        checkers.add(new KeywordIdChecker());
        checkers.add(new NumberChecker());
        checkers.add(new StringChecker());
        checkers.add(new SingleCharChecker(DELIMITERS, DELIMITER));
        checkers.add(new SingleCharChecker(SPACES, SPACE));
        checkers.add(new StringMatchChecker(SINGLE_LINE_COMMENT_SEQUENCE, SINGLE_LINE_COMMENT));

        tokenizer = new DefaultTokenizer(checkers);
    }

    @Override
    public void parse(String content) throws ParseException {
        tokenizer.tokenize(content);
    }

    @Override
    public boolean next() throws ParseException {
        int tokenType;
        boolean doRepeat;

        //handle spaces and single line comments
        do {
            doRepeat = false;
            boolean res = tokenizer.next();

            if (res) {
                tokenType = tokenizer.getTokenType();

                switch (tokenType) {
                    case SPACE:
                        doRepeat = true;

                        break;
                    case SINGLE_LINE_COMMENT:
                        //end of text
                        int currentPosition = tokenizer.getCurrentPosition();
                        int endLinePos = tokenizer.getText().indexOf(NEWLINE, currentPosition);
                        int textSize = tokenizer.getText().length();

                        //if it's the text's end or after new line there's nothing

                        if (endLinePos == -1) {
                            tokenizer.rewindFor(textSize - currentPosition);

                            return false;
                        }

                        if (endLinePos + 1 >= textSize) {
                            tokenizer.rewindFor(textSize - currentPosition + 1);

                            return false;
                        }

                        tokenizer.rewindFor(endLinePos - currentPosition + 1);

                        doRepeat = true;

                        break;
                }
            } else//there's no tokens
                return false;
        } while (doRepeat);

        return true;
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
