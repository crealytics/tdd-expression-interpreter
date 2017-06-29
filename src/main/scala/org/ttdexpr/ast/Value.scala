package org.ttdexpr.ast

import org.ttdexpr.ast.Expr

/**
  * Created by rodrigonc on 22/05/16.
  */
case class Value(value: Int) extends Expr {

  def eval(): Int = value

}
