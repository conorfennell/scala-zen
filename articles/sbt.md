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

### Use specific version of SBT

#### Purpose
Show how to use a specific version of SBT in a project.  

#### File Layout
```
./project
./project/build.properties
```

#### Files
`./project/build.properties`
```INI
sbt.vesion=1.0.4
```

#### SBT commands
```Bash

sbt
```
`sbt`  
- checks for version of SBT in `./project/build.properties`.  
- downloads the version specifed or uses the default one installed globally if no properties present.  
- launches a sbt shell that you can now issue commands to such as `run` `compile` `package`.  

### Set name and version of project

#### Purpose
Show how to set the packaged jar version and name of the jar.

#### File layout
```
./build.sbt
./HelloWorld.scala
```

#### Files
`./build.sbt`
```Scala
name := "hello"
version := "1.0.1"
organization := "com.scala"
```

`./HelloWorld.scala`
```Scala
object HelloWorld extends App {
    println("Hi Martin!")
}
```

#### SBT commands
```Bash
sbt package
```
`sbt package`  
- outputs a jar in target to `/target/scala-2.12/hello_2.12-1.0.1.jar`
