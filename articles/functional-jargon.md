## Functional jargon in Scala

### Arity

Represents the number of parameters passed into a function.

More specific representations:

- A `nullary` function has an arity of zero
- A `unary` function has an arity of one
- A `binary` function has an arity of two
- A `ternary` function has an arity of three
- An `n-ary` function has an arity of n
- A `variable arity` function takes variable number of parameters

```Scala
// nullary
def time = System.nanoTime()

// unary
def addFive(x: Int): Int = x + 5

// binary
def sum(x: Int, y: Int): Int = x + y

// ternary
def ifElse[A](expression: => Boolean, trueOutcome: A, falseOutcome: A): A = if(expression) trueOutcome else falseOutcome

// variable arity
def sumAll(x: Int*): Int = x.foldLeft(0)(_ + _)
sumAll()
sumAll(1)
sumAll(1, 2, 3, 4)

```
