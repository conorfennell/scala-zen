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

