## Design patterns in Scala

## Factory Method - Creational
The factory method abstracts the creation of objects behind a method.  

### Purpose
- Hide complex class instantiations  
- Work as a bounded cache for objects  
- Chooses which class to instanstiate  
- Manages access to shared resources  

### Examples
Companion objects are the most common place to have factory methods.  

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
