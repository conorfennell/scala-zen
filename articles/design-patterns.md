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

### Facade - structural
A facade is a simplified interface to a large body of code.

#### Purpose
- make code easier to use.  
- make code more readable.  
- reduce dependencies on the sub system.  
- wrap a poorly designed collection of API's with one well thought out API.  
- perform additional functionality before and after requesting the subsytem.  

#### Examples

```Scala
// Complex parts
class CPU {
  def freeze() = println("FREEZE")
  def jump(position: Long) = println(s"JUMP TO $position")
  def execute() = println("EXECUTE")
}

class HardDrive {
  def read(addresss: Long, size: Int): Array[Byte] = "datadatadatadata".map(_.toByte).toArray
}

class Memory {
  def load(position: Long, data: Array[Byte]) = println(s"LOADING ${data.map(_.toChar).mkString("")}") 
}

// Facade
class ComputerFacade(private val processor: CPU, private val ram: Memory, private val hardDrive: HardDrive) {
  import ComputerFacade._
  def powerUp() = {
    processor.freeze()
    ram.load(BOOT_ADDRESS, hardDrive.read(BOOT_ADDRESS, SECTOR_SIZE))
    processor.jump(BOOT_ADDRESS)
    processor.execute()
  }
  
}

object ComputerFacade {
  val BOOT_ADDRESS = 100L
  val SECTOR_SIZE = 100
  def apply() = new ComputerFacade(new CPU(), new Memory(), new HardDrive())
}

ComputerFacade().powerUp()
```

### Value Object - Behavioral
Value objects are immutable objects. They are equivalent based on the values contained rather then the reference's being equal.

#### Purpose
- Used as the building blocks for domain driven design
- Being immutable makes them easier to reason about
- Once value objects are equal they remain equal

#### Examples
In scala `case class`es, `tuples` and algerbraic are all value objects.

```Scala
case class Person(name: String, age: Int)
Person("Conor", 32) == Person("Conor", 32) // case class
("Conor", 32) == ("Conor", 32) // tuple
```

### Iterator - Behavioral
Iterator gives a common way to retrieve elements from a collection without knowing its implementation.

#### Purpose
- It is a simple interface
- Widely known
- Hides the implemenation details of the collection

#### Examples
The iterator pattern is part of the standard library in Scala and all the collections have the Iterator interface implemented.

```Scala
trait Iterator[A] {
    def hasNext: Boolean
    def next: A
} 

val option = Some(4)
val optionIterator = option.iterator
println(optionIterator.hasNext)
println(optionIterator.next)
```

### Memento - behavioral
Memento provides a mechanism to revert to a previous state.

#### Purpose
- An objects state should be persisted before it is changed, so it can be restored to that state later.  
- The `orginator` object is responisble for the creation of the `memento` object to preserve encapsulation.  
- A `caretaker` can request a `memento` object from the `orginator` to persist state and also provide the `orginator` with a `memento` to revert to that state.  

#### Examples
Memento 

```Scala
case class MementoPerson(age: Int, name: String)
case class OrginatorGame(memento: MementoPerson)


object Caretaker {
	def setMemento(memento: MementoPerson): OrginatorGame = OrginatorGame(memento)
	def getMemento(orginator: OrginatorGame): MementoPerson = orginator.memento
}


val orginator = Caretaker.setMemento(MementoPerson(33, "Conor Fennell"))
val memento = Caretaker.getMemento(orginator)
println(memento)

```

### Implicit Context

#### Purpose
Pass around contexts that are so common it becomes an implicit expectation.  

* The implicit parameter usually is not generic type, and does not have any type parameters  
* The same implicit is being passed to all sorts of different funcitons with different signatures  
* The implicit value might be mutable. Example might be a Thread pool in an execution context.

#### Examples
`Run piece of work asynchronously`  
* Future's all implicitly need an Execution Context to run work asynchronously  
```Scala
// object Future {
//     def apply[T](body: =>T)(implicit executor: ExecutionContext): Future[T]
// }

// brings an implicit ExecutionContext into scope
import scala.concurrent.ExecutionContext.Implicits.global

// both the Future constructor and map function need an ExecutionContext
Future(1 + 1).map(_ + 4)
```  

`Running an akka stream`  
* Akka streams need an implicit materializer to run streams   
```Scala
import akka.stream.scaladsl.Source
import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
    implicit val actorSystem = ActorSystem("hello")
    implicit val materialize = ActorMaterializer()
    Source(1 to 5)
        .runForeach(_ => println("Hello Martin"))
        .map(_ => actorSystem.terminate())
}
``` 

### Type Class Implicits
Type classes originated in Haskell, a type of adapter that uses Scala's implicits to add some extra capabilities to an existing type without direct coupling.

Mainly used where you are doing a conversion and resolves to an immutable value like Json or Monad.  

```Scala
trait Jsonable[T]{
  def serialize(t: T): Json
}
object Jsonable{
  implicit object StringJsonable extends Jsonable[String]{
    def serialize(t: String) = Json.Str(t)
  }
  implicit object DoubleJsonable extends Jsonable[Double]{
    def serialize(t: Double) = Json.Num(t)
  }
  implicit object IntJsonable extends Jsonable[Int]{
    def serialize(t: Int) = Json.Num(t.toDouble)
  }
}
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

#### Bibliography
[1]Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides. Design Patterns:
Elements of Resusable Object-Oriented Software. Addison-Wesley Professional, 1995