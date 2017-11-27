## Design patterns in Scala

## Factory Method - Creational
The factory method abstracts the creation of objects behind a method.  

### Purpose
- Hide complex class instantiations  
- Work as a bounded cache for objects  
- Chooses which class to instantiate  
- Manages access to shared resources  

### Examples
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


## Singleton - Creational
The singleton pattern restricts a class to have only one instance.

The great thing in Scala, is that the singleton pattern is already inbuilt through `object`s. You do not have to provide your own implementaion.

```Scala
object Singleton {
    def doSingletonStuff(): Unit = println("stuff")
}

Singleton.doSingletonStuff()
```