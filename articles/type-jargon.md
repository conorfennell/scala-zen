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
