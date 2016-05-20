package org.ttdexpr.eval;


import java.util.logging.Logger;

/**
 * Created by rodrigonc on 18/05/16.
 */
public class ExprInterpreter {
    private String input;

    private char[] tokenArray;
    private int tokenPos;
    private int lastValue;

    private final static Logger logger = Logger.getLogger("ExprInterpreter");

    public ExprInterpreter(String input) {
        this.input = input;
        this.tokenArray = input.toCharArray();
        this.tokenPos = 0;
    }

    /*
    * Expr := ['('] (Number | Expr * Expr | Expr + Expr) [')']
    * Number := int
    * */
    public int eval() {

        return 0;
    }

    private Expr buildExpr() {

        return new Expr();
    }

    private char nextToken() {
        String val = "";
        while (tokenArray[tokenPos] == ' ' || tokenArray[tokenPos] == '\n' || tokenArray[tokenPos] == '\t') {
            tokenPos++;
        }

        if (tokenArray[tokenPos] == '(') {
            val = "(";
        }

        if (tokenArray[tokenPos] == ')') {
            val = ")";
        }

        if (Character.isDigit(tokenPos)) {
            StringBuffer acumulator = new StringBuffer();

            while (Character.isDigit(tokenArray[tokenPos])) {
                acumulator.append(tokenArray[tokenPos]);
                tokenPos++;
            }

            try {
                lastValue = Integer.parseInt(acumulator.toString());
            } catch (Exception e) {
                logger.info("There was an error parsing number.");
            }

            val = "num";
        }

        tokenPos++;
        return val;
    }
}
