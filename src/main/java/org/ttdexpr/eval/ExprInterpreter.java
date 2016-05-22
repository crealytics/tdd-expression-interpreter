package org.ttdexpr.eval;

import org.ttdexpr.ast.CompositeExpr;
import org.ttdexpr.ast.Expr;
import org.ttdexpr.ast.Value;
import org.ttdexpr.lexer.Lexer;
import org.ttdexpr.lexer.TokenizerEnum;

import java.util.logging.Logger;

/**
 * Created by rodrigonc on 18/05/16.
 */
public class ExprInterpreter {


    private final static Logger logger = Logger.getLogger("ExprInterpreter");

    private Lexer lexer;

    public ExprInterpreter(String input) {
        this.lexer = new Lexer(input);
    }

    public int eval() {
        try {
            Expr expr = buildAST();
            return expr.eval();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return 0;
    }

    /*
    *   https://en.wikipedia.org/wiki/Parsing_expression_grammar#Examples
    *   Expr    ← Sum
    *   Sum     ← Product (('+' / '-') Product)*
    *   Product ← Value (('*' / '/') Value)*
    *   Value   ← [0-9]+ / '(' Expr ')'
    * */
    private Expr buildAST() throws Exception{
        Expr expr;

        lexer.nextToken();
        if (lexer.getCurrentToken() == TokenizerEnum.EOF) {
            throw new Exception("Unexpected end of expression");
        }
        expr = expr();
        if (lexer.getCurrentToken() != TokenizerEnum.EOF) {
            throw new Exception("Expected end of expression");
        }
        return expr;
    }

    private Expr expr() throws Exception {
        return sum();
    }

    private Expr sum() throws Exception {
        Expr left, right = null;
        TokenizerEnum op = null;

        left = product();

        if (lexer.getCurrentToken() == TokenizerEnum.PLUS || lexer.getCurrentToken() == TokenizerEnum.MINUS) {
            if (lexer.getCurrentToken() == TokenizerEnum.PLUS) {
                op = TokenizerEnum.PLUS;
                lexer.nextToken();
            }

            if (lexer.getCurrentToken() == TokenizerEnum.MINUS) {
                op = TokenizerEnum.MINUS;
                lexer.nextToken();
            }

            if (lexer.getCurrentToken() == TokenizerEnum.NUMBER || lexer.getCurrentToken() == TokenizerEnum.LEFT_BRACKET) {
                right = product();
            }
        }

        return new CompositeExpr(left, right, op);
    }

    private Expr product() throws Exception {
        Expr left, right = null;
        TokenizerEnum op = null;

        left = value();

        if (lexer.getCurrentToken() == TokenizerEnum.MULT || lexer.getCurrentToken() == TokenizerEnum.DIV) {
            if (lexer.getCurrentToken() == TokenizerEnum.MULT) {
                op = TokenizerEnum.MULT;
                lexer.nextToken();
            }

            if (lexer.getCurrentToken() == TokenizerEnum.DIV) {
                op = TokenizerEnum.DIV;
                lexer.nextToken();
            }

            if (lexer.getCurrentToken() == TokenizerEnum.NUMBER || lexer.getCurrentToken() == TokenizerEnum.LEFT_BRACKET) {
                right = value();
            }
        }
        return new CompositeExpr(left, right, op);
    }

    private Expr value() throws Exception {
        Expr expr;

        if (lexer.getCurrentToken() == TokenizerEnum.NUMBER) {
            lexer.nextToken();
            return new Value(lexer.getLastValue());
        }
        if (lexer.getCurrentToken() == TokenizerEnum.LEFT_BRACKET) {
            lexer.nextToken();
        }

        expr = expr();

        if (lexer.getCurrentToken() == TokenizerEnum.RIGHT_BRACKET) {
            lexer.nextToken();
        }

        return expr;
    }


}
