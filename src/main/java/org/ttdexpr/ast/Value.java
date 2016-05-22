package org.ttdexpr.ast;

import org.ttdexpr.ast.Expr;

/**
 * Created by rodrigonc on 22/05/16.
 */
public class Value extends Expr {
    private int value;

    public Value(int value) {
        this.value = value;
    }

    public int eval() {
        return value;
    }
}
