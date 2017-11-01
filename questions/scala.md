1. [Explain by value parameter?](#explain-by-value-parameter)
2. [Explain by name parameter?](#explain-by-name-parameter)
3. [What is Unit?](#what-is-unit)
4. [Differences between def val var lazy](#differences-between-def-val-var-lazy)
5. [What are Nothing Nil None Empty Null null](#what-are-nothing-nil-none-empty-null-null)
6. [What is the uniform access principal](#what-is-the-uniform-access-principal)
7. [What are free and bound variables](#what-are-free-and-bound-variables)
8. [What is a higher order function](#what-is-a-higher-order-function)
9. [What is a first class function](#what-is-a-first-class-function)
10. [What is a companion object](#what-is-a-companion-object)
11. [Is there an if statement in scala](#is-there-an-if-statement-in-scala)
12. [What are the differences between case class and normal class](#what-are-the-differences-between-case-class-and-normal-class)
13. [What would be a trait for a monad in Scala](#what-would-be-a-trait-for-a-monad-in-scala)

## Explain by value parameter?
A by-value parameter is evaluated before the method is invoked. e.g. ```(a: Int)```
```scala
// a and b are by value parameters
def sum(a: Int, b: Int): Int = a + b

sum(3 + 4, 4)
```

## Explain by name parameter?
A by-name parameter is not evaluated before the method is invoked. But each time the parameter is referenced inside the method  e.g. ```(msg: => String)```
```scala
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

```scala
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

```scala
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

```scala
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

## What is a first class function
A first class function is when a language treats a function as a value and can be assigned to variables.

```scala
// a function assigned to a variable
val squared = (x: Int) => x * x

// then set to another variable
val squareInt = squared

println(squared(5))
println(squareInt(5))

```

## What is a companion object
A companion object is a singleton object that shares the same name with a class defined in the same source file.  
Companion objects and classes have access to each other’s private members.

```scala

class User(val name: String, val age: Int)

object User {
  // factory pattern in Scala, do not require nee for instantiation
  def apply(name: String, age: Int): User = new User(name, age)
  // unapply enables the extractor pattern
  def unapply(user: User): Option[(String, Int)] = Option((user.name, user.age))
}

User("Conor", 32) match {
  case User(name, _) => println(name)
  case _ => println("default")
}
```

## Is there an if statement in scala
If statement like functionality is known as `conditional expressions` in Scala.  
The key difference is that the if syntax returns a value, so therefore it is an expression rather than a statement.  
In Java the if statement does not return a value.

```Scala

// conditional expression returns the Boolean value
def isNegative(x: Int): Boolean = if (x < 0) true else false 


// more traditional way outside Scala with statements
def isPositive(x: Int): Boolean = {
  var isPos = false
  if (x > -1) {
    isPos = true
  }
  return isPos  
}
```

## What are the differences between case class and normal class
1. By default case class member variables are `val`.
2. Creates a companion object with `apply` and `unapply` methods.
3. Automatically can use for extractor pattern.
4. Eligible `toString` statement.
5. Sane `equals` which compares the member variables and not the memory reference of the instance.
6. Useful `copy` method.
7. An implemented `hashCode`.

```Scala

case class CaseUser(name: String, age: Int)
class NormalUser(val name: String, val age: Int)

// Calls CaseUser.apply("Conor", 32) which is on the generated companion object
val caseConor = CaseUser("Conor", 32)
// Normal instantiation of a class
val normalConor = new NormalUser("Conor", 32)

// Extractor pattern
caseConor match {
  case CaseUser(name, _) => println(name)
}

// prints: CaseUser(Conor,32)
println(caseConor)
// prints: NormalUser@1b53e6fc
println(normalConor)

// compares the member variables for equality 
println(CaseUser("Conor", 32) == CaseUser("Conor", 32)) // results true

// compares the memory reference
println(new NormalUser("Conor", 32) == new NormalUser("Conor", 32)) // results false


// useful copy method when you want to preserve some fields and return an new instance
def changeName(user: CaseUser, name: String): CaseUser = user.copy(name = name)

println(changeName(caseConor, "James"))

```

## What would be a trait for a monad in Scala
Simply a monad trait in Scala would have two methods
1. `apply` which takes any `A` and wraps it in a monad
2. `flatMap` take a higher order function which converts `A` into a monad of type `F[B]`

From these base methods other methods like `map` and `fold` can be defined.

```Scala

trait Monad[F[_]] {
  def apply[A](value: A): F[A]
  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]
}

```