## SBT Examples
These examples presume you have SBT installed from http://www.scala-sbt.org/

### Hello World

#### Purpose
Excute a scala source file with SBT.

#### File Layout
```
./HelloWorld.scala
```

#### Files
`HelloWorld.scala`
```Scala
object HelloWorld extends App {
    println("Hi Martin!")
}
```

#### SBT commands
```Bash
sbt run
```
`sbt run`  
- compiles any scala files in the directory.  
- packages the files into a jar.  
- executes the jar file with the presumption that one class has `def main(args: String)` function defined.  