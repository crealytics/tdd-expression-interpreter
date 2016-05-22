package org.ttdexpr.lexer;

import java.util.logging.Logger;

/**
 * Created by rodrigonc on 22/05/16.
 */
public class Lexer {
    private String input;

    private char[] tokenArray;
    private int tokenPos;
    private int lastValue;
    private TokenizerEnum currentToken;

    private final static Logger logger = Logger.getLogger("Lexer");

    public Lexer(String input) {
        this.input = input;
        this.tokenArray = input.toCharArray();
        this.tokenPos = 0;
    }

    public void nextToken() {
        if (tokenPos == tokenArray.length) {
            currentToken = TokenizerEnum.EOF;
            return;
        }

        while ((tokenArray[tokenPos] == ' ' || tokenArray[tokenPos] == '\n' || tokenArray[tokenPos] == '\t')) {
            tokenPos++;
        }

        if (Character.isDigit(tokenArray[tokenPos])) {
            StringBuffer acumulator = new StringBuffer();

            while (tokenPos != tokenArray.length && Character.isDigit(tokenArray[tokenPos])) {
                acumulator.append(tokenArray[tokenPos]);
                tokenPos++;
            }

            currentToken = TokenizerEnum.NUMBER;

            try {
                lastValue = Integer.parseInt(acumulator.toString());
            } catch (Exception e) {
                logger.info("There was an error parsing number.");
            }
        } else {
            switch (tokenArray[tokenPos]) {
                case '(':
                    currentToken = TokenizerEnum.LEFT_BRACKET;
                    break;
                case ')':
                    currentToken = TokenizerEnum.RIGHT_BRACKET;
                    break;
                case '+':
                    currentToken = TokenizerEnum.PLUS;
                    break;
                case '-':
                    currentToken = TokenizerEnum.MINUS;
                    break;
                case '/':
                    currentToken = TokenizerEnum.DIV;
                    break;
                case '*':
                    currentToken = TokenizerEnum.MULT;
                    break;
                default:
                    break;
            }
            tokenPos++;
        }
    }

    public int getLastValue() {
        return lastValue;
    }

    public TokenizerEnum getCurrentToken() {

        return currentToken;
    }
}
