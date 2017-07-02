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

  private def sum(): Expr = {
    val left: Expr = product()
    val (opOption, rightOption) = lexer.getCurrentToken match {
      case op @ (TokenizerEnum.PLUS | TokenizerEnum.MINUS) => lexer.nextToken; (Some(op), productIfNumberOption)
      case _ => (None, None)
    }
    new CompositeExpr(left, rightOption, opOption)
  }

  private def productIfNumberOption(): Option[Expr] =
    if (lexer.getCurrentToken == TokenizerEnum.NUMBER ||
          lexer.getCurrentToken == TokenizerEnum.LEFT_BRACKET)
      Some(product())
    else None

  private def product(): Expr = {
    val left: Expr = value()
    val (opOption, rightOption) = lexer.getCurrentToken match {
      case op @ (TokenizerEnum.MULT | TokenizerEnum.DIV) => lexer.nextToken; (Some(op), valueIfNumberOption)
      case _ => (None, None)
    }
    new CompositeExpr(left, rightOption, opOption)
  }

  private def valueIfNumberOption(): Option[Expr] =
    if (lexer.getCurrentToken == TokenizerEnum.NUMBER ||
          lexer.getCurrentToken == TokenizerEnum.LEFT_BRACKET)
      Some(value())
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
