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

###  Higher-Order Functions (HOF)
A higher order function is a function which takes another function as an argument and/or a function which returns another function as a result.

```scala
// map, flatMap and filter are examples of functions which take other functions as arguments
List(1, 2, 3).map(_ + 2)
List(1, 2, 3).flatMap(number => number.to(10).toList)
List(1, 2, 3).filter(number => (number % 2) == 0)

// generateAdder is an example of a function returning another function
def generateAdder(x: Int) = (y: Int) => y + x
val addFive = generateAdder(5)
addFive(4)

```

### Currying
Currying transforms a function that takes multiple parameters into a chain of functions, each taking a subset of the parameters.  
Its achieved by defining parameter lists `(x:Int, t: Int)(y:Int)(z:Int)`.  
The strange term currying is named after the logician [Haskell Curry](https://en.wikipedia.org/wiki/Haskell_Curry).

```Scala

def sum(x:Int)(y: Int): Int = x + y

val sumFive = sum(5)(_)

println(sumFive(5))

```