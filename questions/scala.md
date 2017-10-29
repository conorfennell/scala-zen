1. [Explain by value parameter?](#explain-by-value-parameter)
2. [Explain by name parameter?](#explain-by-name-parameter)
3. [What is Unit?](#what-is-unit)
4. [Differences between def val var lazy](#differences-between-def-val-var-lazy)
5. [What are Nothing Nil None Empty Null null](#what-are-nothing-nil-none-empty-null-null)
6. [What is the uniform access principal](#what-is-the-uniform-access-principal)
7. [What are free and bound variables](#what-are-free-and-bound-variables)
8. [What is a higher order function](#what-is-a-higher-order-function)
9. [What is a first class function](#what-is-a-first-class-function)

## Explain by value parameter?
A by-value parameter is evaluated before the method is invoked. e.g. ```(a: Int)```
```
// a and b are by value parameters
def sum(a: Int, b: Int): Int = a + b

sum(3 + 4, 4)
```

## Explain by name parameter?
A by-name parameter is not evaluated before the method is invoked. But each time the parameter is referenced inside the method  e.g. ```(msg: => String)```
```
trait Logger {
  def info(msg: => String)
  def warn(msg: => String)
  def error(msg: => String)
}

case class Log(var level: String ="ERROR") extends Logger {
  def info(msg: => String) = if (level != "ERROR") println(msg)
  def warn(msg: => String) = if (level == "WARN") println(msg)
  def error(msg: => String) = println(msg)
}

val log = Log()

// the argument passed to `log.warn` is never evaluated
log.warn(s"${Thread.currentThread().getName}: yo there is an error on thread")

```

## What is Unit?
`Unit` is a subtype of `AnyVal`. There is only one value of type `Unit`, `()`, and it is not represented by any object in the underlying runtime system. A method with return type `Unit` is analogous to a Java method which is declared `void`.
```
def logToConsole(msg: => String): Unit = println(msg)
```

## Differences between def val var lazy?
`def` defines a method  
`val` defines a fixed value, it is immmutable and eagerly initialized  
`var` defines a variable reference, it is mutable  
`lazy` only initialised when required and as late as possible, default is strict and is not recomputed like `by-name` parameters

```
// evaluated every time that gets invoked
def z = { println("z"); 3}
// evaluated immeditately once
val x = {println("x"); 1}
// evaluated immeditately
val w = {println("x"); 1}
// evaluated only when invoked, once
lazy val y = { println("y"); 2}
```

## What are Nothing Nil None Empty Null null
`Nothing` is a trait that is the bottom subtype of every subtype of `Any`  
`Nil` is an empty list that is defined as a List[Nothing]  
`None` is an empty Option that is defined as a Option[Nothing]  
`Null` is a trait and is the bottom type similiar to Nothing but only for `AnyRef` not `AnyVal`  
`null` is an instance of the `Null` trait

![Alt text](./class-hierarchy.png?raw=true "Scala class hierarchy")



## What is the uniform access principal
The uniform access principle states that variables, precomputed properties and parameterless functions should be accessed using the same syntax.
Therefore not betraying whether they are implemented through storage or through computation.  
Scala supports this principle by not allowing parentheses to be placed at call sites of parameterless functions
A parameterless function definition can be changed to a val or vice versa, without affecting client code

```
  def nonUniformRandom() = new scala.util.Random().nextInt()
  def uniformRandom = new scala.util.Random().nextInt()
  val rand = 213

  // non uniform access
  nonUniformRandom()
  rand 
  
  // uniform access
  uniformRandom
  rand


  object a {
      def nonUniformRandom() = new scala.util.Random().nextInt()
      def uniformRandom = new scala.util.Random().nextInt()
      val rand = 213
  }

  // non uniform access
  a.nonUniformRandom()
  a.rand 
  
  // uniform access
  a.uniformRandom
  a.rand

```

## What are free and bound variables
Free and bound variables are talked about in the context of a function.  
`bound variables` are arguments to a function that are explicity defined in their function definition.  
`free variables` are variables that are referenced in the function body and are defined outside the function

```
// bound variable function, x is a bound variable
def addOne(x: Int): Int = x + 1

object Counter {
  var counter: Int = 0
  // free variable function, counter is a free variable
  def increment: Int = {
    counter = counter + 1
    counter
  }

  // mix of bound varibale `x` and free variable `counter`
  def resetCounterTo(x: Int): Int = {
    counter = x
    counter
  }
}

println(Counter.increment)


```

## What is a higher order function
A higher order function is either:
1. A function which takes another function as an argument.
2. A function which returns another function as a result.

```
// map, flatMap and filter are examples of functions which take other functions as arguments
List(1, 2, 3).map(_ + 2)
List(1, 2, 3).flatMap(number => number.to(10).toList)
List(1, 2, 3).filter(number => (number % 2) == 0)

// generateAdder is an example of a function returning another function
def generateAdder(x: Int) = (y: Int) => y + x
val addFive = generateAdder(5)
addFive(4)


```

## What is a first class function
A first class function is when a language treats a function as a value and can be assigned to variables.

```
// a function assigned to a variable
val squared = (x: Int) => x * x

// then set to another variable
val squareInt = squared

println(squared(5))
println(squareInt(5))

```
