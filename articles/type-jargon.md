### Type
Types can be variables, expressions and functions.  
Types define and enforce restricted boundries for data structures and components e.g. string, float, function of String to Int.

In scala the type system is checked on compile time.  

```Scala
type Number = Int

```

### Invariance  
Invariance in a type system means it is locked to one type and it cannot be assigned a super class or a sub class.  

`arrays` in Scala are invariant, in that they can be only one type. 

```Scala
sealed trait Animal
case class Dog() extends Animal
case class Cat() extends Animal

Array(Dog(), Cat())

```