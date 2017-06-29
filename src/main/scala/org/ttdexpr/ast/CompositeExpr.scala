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
    var rightValue: Int = 0
    if (right == null) {
      if (op == PLUS || op == MINUS) {
        rightValue = 0
      }
      if (op == MULT || op == DIV) {
        rightValue = 1
      }
    } else {
      rightValue = right.eval()
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
