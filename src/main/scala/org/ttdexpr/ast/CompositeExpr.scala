package org.ttdexpr.ast

import org.ttdexpr.lexer.TokenizerEnum

import org.ttdexpr.lexer.TokenizerEnum._

/**
  * Created by rodrigonc on 22/05/16.
  */
case class CompositeExpr(left: Expr, right: Expr, var op: TokenizerEnum) extends Expr {

  def eval(): Int = {
    if (op == null) {
      op = INVALID
    }
    val rightValue: Int =
      if (right == null) {
        if (op == MULT || op == DIV) 1
        else 0
      } else {
        right.eval()
      }
    op match {
      case PLUS => left.eval() + rightValue
      case MINUS => left.eval() - rightValue
      case DIV => left.eval() / rightValue
      case MULT => left.eval() * rightValue
      case _ => left.eval()

    }
  }

}
