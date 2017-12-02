## Design patterns in Scala
`Design Patterns are not language agnostic`  
“One person’s pattern can be another person’s primitive building block.”[1,p.3]  
“The choice of programming language is important because it influences one’s point of view ... that choice determines what can and cannot be implemented easily.”[1, p.4]  

### Factory Method - Creational
The factory method abstracts the creation of objects behind a method.  

#### Purpose
- Hide complex class instantiations  
- Work as a bounded cache for objects  
- Chooses which class to instantiate  
- Manages access to shared resources  

#### Examples
In Scala companion objects are the most common place to have factory methods.  
When case classes are defined an `apply` factory method is automatically created on the companion object.   

`Companion object apply method`
```Scala
class Person(val age: Int, val name: String)

object Person {
    def apply(age: Int, name: String) = new Person(age, name)
}

Person.apply(32, "Conor")
Person(32, "Conor")
```

```Scala
sealed trait Animal
private class Cat() extends Animal
private class Dog() extends Animal

object Animal {
    def apply(kind: String): Animal = kind match {
        case "dog" => new Dog()
        case "cat" => new Cat()
    }
}

Animal("cat")
Animal("dog")
```


### Singleton - Creational
The singleton pattern restricts a class to have only one instance, and provide a global point of access to it.

In Scala an `object` defines a singleton object, there is only one instance of this object in any given program. Therefore it is inbuilt into the language.

```Scala
object Singleton {
    def doSingletonStuff(): Unit = println("stuff")
}

Singleton.doSingletonStuff()
```

### Lazy Initialization - Creational
The lazy initialization pattern only initilaizes a value or object on its first access.

Scala has this inbuilt into the language using the `lazy` keyword.

#### Purpose
- Delay expensive computation   
- Avoid expensive compitation  

#### Examples
`object`'s in Scala are lazy initialized.

```Scala
lazy val x =  {
        println("Only printed on first access")
        2
    }

val y =  {
    println("Evaluated immediately") // prints to console here
    2
}

x // prints to console here

```

### Builder - Creational
The builder pattern is used for creating complex objects with a lot of attributes for its constructor.

Scala has a language feature that removes a bit the need of the classical builder pattern by using named arguments.

#### Purpose
- Make construction of objects more readable

#### Examples

```Scala
case class Computer(isOn: Boolean, hasRam: Boolean, hasCpu: Boolean, hasMotherBoard: Boolean, hasOperatingSystem: Boolean)
Computer(true, true, true, true, true) // not readable

// name arguments
Computer(
    isOn = true,
    hasRam = true,
    hasCpu = true,
    hasMotherBoard = true,
    hasOperatingSystem = true
)
```

### Prototype - Creational
The prototype pattern clones a new instance of a class from an existing instance.

With case classes in Scala this is automatically implemented through the copy method.

#### Purpose
- Avoid subclasses of an object creator in the client application
- Avoid the inherent cost of creating a new object in the standard way aka the `new` keyword.

#### Examples
```Scala
trait Prototype[A] {
    def clone(): A
}

class Person(val name: String, val age: Int) 
extends Prototype[Person] {
    def clone():Person = new Person(this.name, this.age)
}

val person = new Person("Conor", 32)
person.clone()

case class Dog(val name: String, val age: Int) 
val dog = Dog("Conor", 32)
dog.copy()
```

### Adapter - Structural
The adpater pattern enriches an existing interface into a more expected interface.

Scala has this inbuilt using `implicit classes`.

#### Purpose
- Adpaters are useful for integrating existing components

#### Examples

```Scala
trait Log {
    def warning(message: String)
    def error(message: String)
}

final class Logger {
    def log(level: String, message: String) {
        println(s"$level $message")
    }
}

implicit class LoggerToLogAdapter(logger: Logger) extends Log {
    def warning(message: String) {
        logger.log("WARNING", message)
    }

    def error(message: String) {
        logger.log("ERROR", message)
    }
}

val log: Log = new Logger()

log.warning("messsage")
log.error("message")
```

#### Bibliography
[1]Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides. Design Patterns:
Elements of Resusable Object-Oriented Software. Addison-Wesley Professional, 1995