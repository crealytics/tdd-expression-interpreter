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

  private var tokenArray: Array[Char] = input.toCharArray()

  private var tokenPos: Int = 0

  @BeanProperty
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
      val acumulator: StringBuffer = new StringBuffer()
      while (tokenPos != tokenArray.length &&
               java.lang.Character.isDigit(tokenArray(tokenPos))) {
        acumulator.append(tokenArray(tokenPos))
        tokenPos += 1
      }
      currentToken = TokenizerEnum.NUMBER
      try lastValue = java.lang.Integer.parseInt(acumulator.toString)
      catch {
        case e: Exception => logger.info("There was an error parsing number.")
      }
    } else {
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
