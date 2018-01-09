Used for dependency injection.

1. Injector
* a container for the bindings  

2. Module
* a place where you define the bindings
* DSL for bindings
* extends `Injector`  

3. Injectable  
* DSL for injection
`inject [SomeClass]`  
`inject [Database] (identified by 'remote' and by default new Riak)`  

### Injector and Injectable  
```Scala
import scaldi.Injector
import scaldi.Injectable

class OfficialMessageService(implicit inj: Injector) extends MessageService with Injectable {
    val officialGreeting = inject [String] (identified by "greeting.official")
    
    def getGreetMessage(name: String) = s"$officialGreeting, $name!"
}
```

### Module  
```Scala
class UserModule extends Module {
    bind [MessageService] to new OfficialMessageService

    binding identifiedBy "greeting.official" to "Welcome" 
}
```  

### Bindings  
```Scala
// Lazy bindings
bind [ActorSystem] to new ActorSystem
```
```Scala
// Non lazy bindings
bind [Server] toNonLazy new HttpServer
```
```Scala
// Provider binding
bind [Connection] toProvider inject[Database].getConnection
```
