# tdd-expression-interpreter

This is an implementation of a Dojo Puzzle exercise that can be found here: http://dojopuzzles.com/problemas/exibe/avaliando-expressoes-matematicas/

Translated exercise below:

```
Given a math expression using infix notation, evaluate its result. For example:
3 * ( 2 + 5 ) = 21
3 * ( 2 + 5 * 2) = 36

Hint: In the usual infix notation, operators are written between operands. In postfix notations, the operators are written after operands.
Infix notation: 3 * ( 2 + 5)
Postfix notation: 3 2 5 + *
```

Currently, the interpreter implentation is following the standard of my old compiler implementations. 

It supports the four basic operations: +, -, *, /

It was built using TDD, to test it use the following maven command:
```
mvn clean test
```
