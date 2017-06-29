package org.ttdexpr.lexer

import java.util.logging.Logger

import Lexer._

import scala.beans.{BeanProperty, BooleanBeanProperty}

//remove if not needed
import scala.collection.JavaConversions._

object Lexer {

  private val logger: Logger = Logger.getLogger("Lexer")

}

/**
  * Created by rodrigonc on 22/05/16.
  */
class Lexer(private var input: String) {

  private var tokenArray: String = input

  private var tokenPos: Int = 0

  // This annotation auto-generates getters (for both var and val) and setters (for var)
  @BeanProperty
  // The underscore initializes to the default value which is null for objects and 0 for primitive numeric types
  var lastValue: Int = _

  @BeanProperty
  var currentToken: TokenizerEnum = _

  def nextToken(): Unit = {
    if (tokenPos == tokenArray.length) {
      currentToken = TokenizerEnum.EOF
      return
    }
    while (
      (tokenArray(tokenPos) == ' ' ||
         tokenArray(tokenPos) == '\n' ||
         tokenArray(tokenPos) == '\t')) {
      tokenPos += 1
    }

    if (java.lang.Character.isDigit(tokenArray(tokenPos))) {
      // takeWhile method comes from the implicit wrapper class StringOps
      // which among many other nice additions allows you to treat a String
      // like a collection of characters.
      // Notice also, that Character.isDigit is automatically lifted from a method to a Function.
      val digits = tokenArray.substring(tokenPos).takeWhile(java.lang.Character.isDigit)
      tokenPos += digits.length
      currentToken = TokenizerEnum.NUMBER
      try lastValue = java.lang.Integer.parseInt(digits)
      catch {
        // Exception handling is done via pattern matching
        case e: Exception => logger.info("There was an error parsing number.")
      }
    } else {
      // In contrast to Java's switch ... case, Scala's match case doesn't need break statements
      tokenArray(tokenPos) match {
        case '(' => currentToken = TokenizerEnum.LEFT_BRACKET
        case ')' => currentToken = TokenizerEnum.RIGHT_BRACKET
        case '+' => currentToken = TokenizerEnum.PLUS
        case '-' => currentToken = TokenizerEnum.MINUS
        case '/' => currentToken = TokenizerEnum.DIV
        case '*' => currentToken = TokenizerEnum.MULT
        case _ => //break
      }
      tokenPos += 1
    }
  }

}
