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

### Free and Bound variables
Free and bound variables are variables referenced in a function body.  
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

### Closure
A function which has free variables bound are known as closures.
Even if the scope of which the variable was defined has exited it will still exist if the closure function is within scope.

```Scala
val increment = {
    var counter = 0
    () => {
        counter = counter + 1
        counter
        }
    
}

println(increment())

```

### Partial Application
A partially applied function is a function where some of the parameters are applied and it returns a function defined with just the unapplied parameters.  
Underscore followed by its type `_ : Int` is used to desiginate the unapplied parameters.

```Scala
val enclose = (prefix:String, inner:String, postfix:String) => s"$prefix$inner$postfix"

val div = enclose("<div>", _ : String, "</div>")
val p = enclose("<p>", _ : String, "</p>")

println(div(p("Hello World!")))

```


### Functor
Functors are types which define a map function. The map function adheres to two laws: `preserve identity` and `composablity`.
```Scala
trait Functor[A] {
    def map[B](f: A => B): Functor[B]
}
```
`Preserve Identity` When an idenity functor is passed to a map. The resulting functor will be equal to the original functor.
```Scala
// an identity function simply returns the argument passed in without changing it
def identity(x: Int) = x
List(1, 2, 3).map(identity)  == List(1, 2, 3)
```
`Composable` composing two functions and then mapping the resulting function over a functor should be the same as first mapping one function over the functor and then mapping the other one. `functor.map(x => f(g(x))) = functor.map(g).map(f)`
```Scala
def addOne(x: Int) = x + 1
def multiplyByFive(x: Int) = x * 5

val nums = List(1, 2, 3)

nums.map(num => multiplyByFive(addOne(num))) == nums.map(addOne).map(multiplyByFive)

```

Examples of functors in the scala library are `List`, `Try` and `Option`.


### Referential Transparency
Is the ability to replace an expression by its resulting value and not change the behaviour of a program.

`Referentially transparent`
```Scala
def add(x:Int, y: Int): Int = x + y

// this is referentially transparent
val sum = add(3, 4)
// and can be replaced with
val sum = 7

```

`Not Referentially transparent`
```Scala
var counter = 0

def addAndCount(x:Int, y: Int): Int = {
  counter = counter + 1
  x + y
}

// this is not referentially transparent
val sum = addAndCount(3, 4)
val counterSum = _sum + counter
println(counterSum) // prints 8

counter = 0 // reset

// cannot be replaced with 
val _sum = 7
val _counterSum = 7 + 0
println(_counterSum) // prints 7
```

### Lambda
Lambda is an anonymous function that can be passed around like a value.
```Scala
val sum = (x:Int, y:Int) => x + y
sum(3, 4)
```
They are often passed into Higher Order Functions
```Scala
List(1, 2, 3)
  .map((num: Int) => num * 10)
```

### Algerbraic Data Types
ADT's approach structuring your data as `products` and `sums`.

Sum types are `this` OR `that`  
Product types are `this` AND `that` 

As an example when modelling ADT's for renewable power plants.  

A power plant can be solar `OR` wind.  
A power source can be solar panels `OR` turbines. 

A power plant has many solar panels `AND` a support contractor.
 
`Solar` OR `Wind`  
`SolarPanel` OR `Turbine`  
`powerPanels` AND `supportContractor`

Example algerbraic data types in the core scala library `Option`, `Either`, `Try` and `List` 

```Scala
sealed trait RenewablePlant {
  val powerSources: Seq[PowerSource]
  val supportContractor: String
}
sealed trait PowerSource

case class SolarPanel() extends PowerSource
case class Turbine() extends PowerSource

case class Solar(powerSources: Seq[SolarPanel], supportContractor: String) extends RenewablePlant

case class Wind(powerSources: Seq[Turbine], supportContractor: String) extends RenewablePlant

def identify(plant: RenewablePlant): Unit = plant match {
  case Solar(_, _) => println("SUN SUN SUN")
  case Wind(_, _) => println("WIND WIND WIND")
}

identify(Solar(List(), "Jim"))
identify(Wind(List(), "Mark"))

```

### Value

A value is the result of an expression which can be assigned to a variable.

Examples of a value
```Scala
// the lambda function here is a value
val add = (x:Int, y:Int) => x + y

// 3 is a value
val x = 3

```

### Side Effect
A function is said to have a side effect if it read or wrote to external mutable state.

```Scala
// no side effect, pure function
def add(x:Int, y:Int): Int = x + y

// side effects by printing
def addPrint(x:Int, y:Int): Int = {
  println(x + y)
  x + y
}
```

### Pure
A pure function is said to have no side effects and is not allowed to read from any mutual external state.

```Scala
// no side effect, pure function
def add(x:Int, y:Int): Int = x + y
```

### Idempotent
An idempotent function can cause idempotent side-effects.  
When calling an idempotent function multiple times with the same parameters, it will produce the same result and any of its side-effects will produce the same results.

The function below is idempotent, since the number stays removed from the set after subsequent calls i.e. additional calls do nothing. 
```Scala
var set = Set(1, 2)

def removeFromSet(x:Int): Boolean = {
  set = set.-(x)
  set.contains(x)
  } 
removeFromSet(2)
removeFromSet(2)
removeFromSet(2)

```

### Constant
A constant is a variable which can not be reassigned once assigned.  
In Scala a variable is made constant using the `val` keyword.  

Constants are [referentially transparent](#referential-transparency).
```Scala
val Five = 5
val sum = (x:Int, y:Int): Int => x + y
```

### Semigroup
Formally, a type can be called a semigroup if it has:  
- a combine operation with type (A, A) => A  
- adheres to the rules of associatively when combining  

Examples of semigroups are  
- Concatenation of strings `{"a" + "b"} + "c" == "a" + {"b" + "c"}`  
- Addition of integers `{1 + 2} + 3 == 1 + {2 + 3}`  
- Oring of Booleans `{true || false} || false == true || {false || false}`  
```Scala
// def combine[A](a: A, b: A): A
def stringConcatSemigroup(a: String, b: String): String = s"$a$b"

def integerAddSemigroup(x: Int, y: Int): Int = x + y

def booleanAndSemigroup(a: Boolean, b: Boolean): Boolean = a && b


// assoicative laws held (x combine y) combine z = x combine (y combine z)
stringConcatSemigroup("x", stringConcatSemigroup("y", "z")) == stringConcatSemigroup(stringConcatSemigroup("x", "y"), "z")

integerAddSemigroup(1, integerAddSemigroup(2, 3)) == integerAddSemigroup(integerAddSemigroup(1, 2), 3)

booleanAndSemigroup(true, booleanAndSemigroup(false, true)) == booleanAndSemigroup(booleanAndSemigroup(true, false), true)
```

### Lift
The term `lifting` comes from category theory.  
It is the ability to convert a function of `A => B` to a lifted function `F[A] => F[B]` where it can be applied to a [functor](#functor) or monad `F[A]`    

A concrete example using the monad container `Option`:  
Assume we have a function that converts a string to an integer and the string is wrapped in an `Option` monad. Here we cannot pass in the `Option("4")` in to the function because of incompatiable types. So we have to lift the function to be compatiable.  
```Scala
def stringToInt(s: String): Int = s.toInt
val four: Option[String] = Option("4")

def lift[A, B](f: A => B): Option[A] => Option[B] = (a: Option[A]) => a.map(f)

val optionStringToInt = lift(stringToInt)
optionStringToInt(four)
```

### Domain Codomain
A function maps one set, called a `domain`, to another set, called the `codomain`.  
A function associates every element in the domain with exactly one element in the codomain. In Scala, both domain and codomain are known as `types`.
```Scala
// domain and codomain are both Ints
def increment(num: Int): Int = num + 1

// domain and codomain are Strings
def upperCase(s: String): String = s.toUpperCase

// domain is type Int and the codomain is type string
def intToString(num: Int): String = num.toString

```

### Morphism
Is a mapping from `type` to `type`

#### Endomorphism
A mapping where the input type is the same as the output type.  
```Scala
def upperCase(s: String): String = s.toUpperCase

def increment(num: Int): Int = num + 1 
```

#### Isomorphism
A pair of transformations between 2 types of objects that is structural in nature and no data is lost.  
For example, 2D coordinates could be stored as an array `[2,3]` or object `{x: 2, y: 3}`
```Scala
// Providing functions to convert in both directions makes them isomorphic.
case class Coords(x: Int, y: Int)

val pairToCoords = (pair: (Int, Int)) => Coords(pair._1, pair._2)

val coordsToPair = (coods: Coords) => (coods.x, coods.y)

coordsToPair(pairToCoords((1, 2)))

pairToCoords(coordsToPair(Coords(1, 2)))
```

### Predicate
A predicate is a function which takes a value and returns a boolean `(a:A):Boolean`
Commonly used for filtering.  
`something which is affirmed or denied concerning an argument of a proposition.`
```Scala
def isEven(a: Int):Boolean = (a % 2) == 0

Array(1, 3, 4, 5, 6).filter(isEven)
```

### Auto Currying
Transforming a function that takes multiple arguments into one that if given less than its correct number of arguments returns a function that takes the rest. When the function gets the correct number of arguments it is then evaluated.
```Scala
val add = (x: Int, y: Int) => x + y

val curriedAdd = add.curried
curriedAdd(2) // (y) => 2 + y

curriedAdd(1)(2)
```

### Identity Function
An identity function takes one argument and returns that argument without modification.  
There is an identity function defined in Scala [Predef](https://www.scala-lang.org/api/current/scala/Predef$.html)
```Scala
def identity[A](a: A): a

List(List(1), List(2, 3), List(4, 5)).flatMap(identity) // List(1, 2, 3, 4, 5)
```

### Monoid
A monoid is a [Semigroup](#semigroup) with an empty element, in math it is known as an identity element.   

Formally, a monoid has the properties of a semigroup:  
- a combine operation with type (A, A) => A  
- adheres to the rules of associatively when combining    

With the additional property:  
- an empty element of type A  

```Scala
// def combine[A](a: A, b: A): A
val emptyString: String = ""
def stringConcatSemigroup(a: String, b: String = emptyString): String = s"$a$b"

val emptyAddInteger = 0
def integerAddSemigroup(x: Int, y: Int = emptyAddInteger): Int = x + y

val emptyAndBoolean = true
def booleanAndSemigroup(a: Boolean, b: Boolean = emptyAndBoolean): Boolean = a && b


// assoicative laws held (x combine y) combine z = x combine (y combine z)
stringConcatSemigroup("x", stringConcatSemigroup("y", "z")) == stringConcatSemigroup(stringConcatSemigroup("x", "y"), "z")

integerAddSemigroup(1, integerAddSemigroup(2, 3)) == integerAddSemigroup(integerAddSemigroup(1, 2), 3)

booleanAndSemigroup(true, booleanAndSemigroup(false, true)) == booleanAndSemigroup(booleanAndSemigroup(true, false), true)
```

### Setoid
Is a type which can be compared with instances of the same type for equality.  

Make array a setoid, therefore equivalent if sorted and has the same elements.
```Scala
trait Eq[A] {
  def eqv(X: A, y: A): Boolean
}

object Eq {
  def apply[A](implicit ev: Eq[A]) = ev

  implicit def arrayInstances[B]: Eq[Array[B]] = new Eq[Array[B]] {
    def eqv(xs: Array[B], ys: Array[B]): Boolean =
      xs.zip(ys).foldLeft(true) {
        case (isEq, (x, y)) => isEq && x == y
      }
  }

  implicit class EqOps[A](x:A) {
    def eqv(y: A)(implicit ev: Eq[A]) = ev.eqv(x, y)
  }
}

import Eq._
Array(1, 3) == Array(1, 3) // false

Array(1, 3).eqv(Array(1, 3)) // true

Array(1, 3).eqv(Array(0)) // false
``` 

### Monad
Monads are types which define a flatMap and pure function. The flatMap function adheres to three laws: `left identity`, `right identity` and `associativity`.

```Scala
trait Monad[A] {
  def pure(value: A): Monad[A]
  def flatMap[B](f: A => Monad[B]): Monad[B]
}
```
Examples of Monads in Scala are `Option` and `List`.  
With Option and List the `apply` function is the equivalent of `pure`.  

`Left Identity` Calling pure on type A followed by a flatmapping over f is the same as calling f on type A 
```Scala
val a = 1
def f(x: Int): Option[Int] = Option.apply(x + 1)

Option.apply(a).flatMap(f) == f(a)
```

`Right Identity` a monad flatmapping over pure results in the same monad
```Scala
val a = 1
def f(x: Int): Option[Int] = Option.apply(x + 1)

val monad = Option.apply(a)

monad.flatMap(Option.apply) == monad
```

`Associativity` flat mapping over function f and g is the same as flat mapping over f and then g.
```Scala
val a = 1
def f(x: Int): Option[Int] = Option.apply(x + 1)
def g(x: Int): Option[Int] = Option.apply(x * 2)

val monad = Option.apply(a)

monad.flatMap(f).flatMap(g) == monad.flatMap(f(_).flatMap(g))
```

### Immutability
Immutablility is a variable reference or object whose state cannot be modified after it is created.  
Scala is not strict on immutability and allows mutable variables and mutable objects too.

- A reference can be declared immutable `val` or mutable `var` 
- Scala provides `immutable collection` and `mutable collection`  

| |collection.immutable|collection.mutable|
|-|------|---------|
|val|😃|😓 |
|var|😐|💩|

```Scala
// Order of preference
// 1 val --> immutable
val valImmutable = scala.collection.immutable.Set(0)
// 2 var --> immutable
var varImmutable = scala.collection.immutable.Set(0)
// 3 val --> mutable
val valMutable = scala.collection.mutable.Set(0)
// 4 var --> mutable
var varMutable = scala.collection.mutable.Set(0)
```

### Equational Reasoning
Equational reasoning is enabled by [Referential Transparency](#referential-transparency).
When an application is composed of expressions which Scala can achieve and devoid of side effects, truths about the system can be derived from the parts.