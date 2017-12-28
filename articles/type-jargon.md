### Type
Types can be variables, expressions and functions.  
Types define and enforce restricted boundries for data structures and components e.g. string, float, function of String to Int.

In scala the type system is checked on compile time.  

```Scala
type Number = Int

```

### Invariance  
Invariance in a type system means the allowable type is held to the specified type and its subtypes.  

Mutable collections in Scala are made invariant `[A]` to preserve type safety. Array, ArrayBuffer and ListBuffer are all defined with invariant type parameters.
```Scala
class Array[T]
class ArrayBuffer[A]
class ListBuffer[A]
```

If they were not invariant they would break type safety and it would be possible to do something like the example below.  

```Scala
val ints: Array[Int] = Array[Int](1,2,3)
val anys: Array[Any] = ints
anys(0) = 2.54
```

### Covariance
Immutable collections in Scala are made covariant `[+A]` to make them more flexible, they could also be declared as invariant.  
```Scala
class List[+T]
class Vector[+A]
class Seq[+A]
```

```Scala
sealed trait Animal
case class Dog() extends Animal
case class Cat() extends Animal

val animals: List[Animal] = List[Cat](Cat()) 
```
