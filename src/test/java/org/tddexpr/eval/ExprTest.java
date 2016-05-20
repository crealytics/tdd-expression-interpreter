package org.tddexpr.eval;

import org.junit.Assert;
import org.junit.Test;
import org.ttdexpr.eval.ExprInterpreter;

/**
 * Created by rodrigonc on 18/05/16.
 */
public class ExprTest {
    @Test
    public void testExprWithOneValueTest() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("5");

        Assert.assertEquals(5, exprInterpreter.eval());
    }

    @Test
    public void testExprWithMultiplicationTest() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("5 * 2");

        Assert.assertEquals(10, exprInterpreter.eval());
    }

    @Test
    public void testExprWithPlusTest() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("7 + 10");

        Assert.assertEquals(17, exprInterpreter.eval());
    }

    @Test
    public void testExprWithMinusTest() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("20 - 4");

        Assert.assertEquals(16, exprInterpreter.eval());
    }

    @Test
    public void testExprComposite1() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("3 * (2 + 4)");

        Assert.assertEquals(21, exprInterpreter.eval());
    }

    @Test
    public void testExprComposite2() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("3 * ( 2 + 5 * 2)");

        Assert.assertEquals(36, exprInterpreter.eval());
    }

    @Test
    public void testExprComposite3() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("(3 * ( 2 + 5 * 2))");

        Assert.assertEquals(36, exprInterpreter.eval());
    }


    @Test
    public void testExprComposite4() {
        ExprInterpreter exprInterpreter = new ExprInterpreter("(3 - 4) * (3 * ( 2 + 5 * 2))");

        Assert.assertEquals(-36, exprInterpreter.eval());
    }
}
