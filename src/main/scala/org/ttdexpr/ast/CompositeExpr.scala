package org.ttdexpr.ast

import org.ttdexpr.lexer.TokenizerEnum

import org.ttdexpr.lexer.TokenizerEnum._

/**
  * Created by rodrigonc on 22/05/16.
  */
case class CompositeExpr(left: Expr, right: Option[Expr], val op: Option[TokenizerEnum]) extends Expr {

  def eval(): Int = {
    val rightValue: Option[Int] = right.map(_.eval)
    op match {
      case Some(PLUS) => left.eval() + rightValue.getOrElse(0)
      case Some(MINUS) => left.eval() - rightValue.getOrElse(0)
      case Some(DIV) => left.eval() / rightValue.getOrElse(1)
      case Some(MULT) => left.eval() * rightValue.getOrElse(1)
      case _ => left.eval()

    }
  }

}
