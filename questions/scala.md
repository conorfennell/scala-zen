1. [Explain by value parameter?](#explain-by-value-parameter)
2. [Explain by name parameter?](#explain-by-name-parameter)
3. [What is Unit?](#what-is-unit)
4. [Differences between def val var lazy](#differences-between-def-val-var-lazy)
5. [What are Nothing Nil None Empty Null null](#what-are-nothing-nil-none-empty-null-null)

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
`None` is an empty Option that is deifned as a Option[Nothing]  
`Null` is a trait and is the bottom type similiar to Nothing but only for `AnyRef` not `AnyVal`  
`null` is an instance of the `Null` trait

![Alt text](./class-hierarchy.png?raw=true "Scala class hierarchy")
