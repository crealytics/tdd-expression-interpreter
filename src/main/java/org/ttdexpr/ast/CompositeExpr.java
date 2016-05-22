package org.ttdexpr.ast;

import org.ttdexpr.lexer.TokenizerEnum;

import static org.ttdexpr.lexer.TokenizerEnum.*;

/**
 * Created by rodrigonc on 22/05/16.
 */
public class CompositeExpr extends Expr {
    private Expr left;
    private Expr right;
    private TokenizerEnum op;

    public CompositeExpr (Expr left, Expr right, TokenizerEnum op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public int eval() {
        if (op == null) {
            op = INVALID;
        }

        int rightValue = 0;
        if (right == null) {
            if (op == PLUS || op == MINUS) {
                rightValue = 0;
            }

            if (op == MULT || op == DIV) {
                rightValue = 1;
            }
        } else {
            rightValue = right.eval();
        }

        switch (op) {
            case PLUS:
                return left.eval() + rightValue;
            case MINUS:
                return left.eval() - rightValue;
            case DIV:
                return left.eval() / rightValue;
            case MULT:
                return left.eval() * rightValue;
            default:
                return left.eval();
        }
    }
}
