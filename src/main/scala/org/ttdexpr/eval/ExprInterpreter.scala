package org.ttdexpr.eval

import org.ttdexpr.ast.CompositeExpr
import org.ttdexpr.ast.Expr
import org.ttdexpr.ast.Value
import org.ttdexpr.lexer.Lexer
import org.ttdexpr.lexer.TokenizerEnum
import java.util.logging.Logger
import ExprInterpreter._

//remove if not needed
import scala.collection.JavaConversions._

object ExprInterpreter {

  val logger: Logger = Logger.getLogger("ExprInterpreter")

}

/**
  * Created by rodrigonc on 18/05/16.
  */
class ExprInterpreter(input: String) {
  private val lexer: Lexer = new Lexer(input)

  def eval(): Int = {
    val expr: Expr = buildAST()
    expr.eval()
  }

  /*
   *   https://en.wikipedia.org/wiki/Parsing_expression_grammar#Examples
   *   Expr    ← Sum
   *   Sum     ← Product (('+' / '-') Product)*
   *   Product ← Value (('*' / '/') Value)*
   *   Value   ← [0-9]+ / '(' Expr ')'
   * */

  private def buildAST(): Expr = {
    lexer.nextToken()
    if (lexer.getCurrentToken == TokenizerEnum.EOF) {
      throw new Exception("Unexpected end of expression")
    }
    val theExpr: Expr = expr()
    if (lexer.getCurrentToken != TokenizerEnum.EOF) {
      throw new Exception("Expected end of expression")
    }
    theExpr
  }

  private def expr(): Expr = sum()

  private def sum(): Expr =
    compositeExpression(product(), TokenizerEnum.PLUS, TokenizerEnum.MINUS)

  private def product(): Expr =
    compositeExpression(value(), TokenizerEnum.MULT, TokenizerEnum.DIV)

  private def compositeExpression(childExpression: => Expr, tokens: TokenizerEnum*) = {
    val left: Expr = childExpression
    val (opOption, rightOption) = lexer.getCurrentToken match {
      case op if tokens.contains(op) => lexer.nextToken; (Some(op), ifNumberOption(childExpression))
      case _ => (None, None)
    }
    new CompositeExpr(left, rightOption, opOption)
  }

  private def ifNumberOption(calc: => Expr): Option[Expr] =
    if (lexer.getCurrentToken == TokenizerEnum.NUMBER ||
          lexer.getCurrentToken == TokenizerEnum.LEFT_BRACKET)
      Some(calc)
    else None

  private def value(): Expr = {
    if (lexer.getCurrentToken == TokenizerEnum.NUMBER) {
      lexer.nextToken()
      return new Value(lexer.getLastValue)
    }
    if (lexer.getCurrentToken == TokenizerEnum.LEFT_BRACKET) {
      lexer.nextToken()
    }
    val theExpr: Expr = expr()
    if (lexer.getCurrentToken == TokenizerEnum.RIGHT_BRACKET) {
      lexer.nextToken()
    }
    theExpr
  }
}
