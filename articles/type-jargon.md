### Type
Types can be variables, expressions and functions.  
Types define and enforce restricted boundries for data structures and components e.g. string, float, function of String to Int.

In scala the type system is checked on compile time.  

```Scala
type Number = Int

```

### Invariance - nonvariant
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
sealed trait Animal {
    def speak: Unit
}
class Dog() extends Animal {
    def speak = println("Dog says woof")
}
class DogCat() extends Dog {
 override def speak = println("DogCat says meow")
}

val animals: List[Animal] = List[DogCat]( new DogCat())

def speakInvariant(array: Array[Dog]) {
  array.foreach(_.speak)
}

def speakCovariant(array: Seq[Dog]) {
  array.foreach(_.speak)
}

speakInvariant(Array(new Dog()))
speakCovariant(Seq[DogCat](new DogCat())) 

// compile error with invariant Array
speakInvariant(Array[DogCat](new DogCat())) 
```

### Contravariance  
Contravariance is essentially the opposite of covariance, and is rarely used. 
It is used in Scala's Functional trait.

```Scala
trait Function1[-T, +R]

```

### Value Type 
A value type is any subclass of AnyVal such as Int, Double, or Unit. This term has meaning at the level of Scala source code. At run-time, instances of value types that correspond to Java primitives types may be implemented in terms of primitive type values or instances of wrapper types, such as java.lang.Integer. Over the lifetime of value type instance, the runtime may transform it back and forth betweeen primitive and wrapper types (i.e. to box and unbox it)

* must have exactly one val parameter  
* can define methods, but no val or var fields, nested traits, classes or objects;
cannot be extended.  

A library of value types 
https://github.com/fthomas/refined

```Scala
import scala.language.implicitConversions
class PositiveInt(val value: Int) extends AnyVal

object PositiveInt {
   def apply(value: Int): PositiveInt = {
     require(value >= 0, "It is a negative number")
     new PositiveInt(value)
   }
   implicit def intToPositiveInt(value: Int): PositiveInt = apply(value)
}

import PositiveInt.intToPositiveInt

val x: PositiveInt = -3
```

### Type Parameter  
A type parameter to a generic class or generic function that must be filled in by a type.

```Scala
// List[T] is a type parameter to a generic class
val list: List[String] = List("action")

// count[T] is a type parameter to a generic function
def count[T](list: List[Option[T]]) = list.size 

```