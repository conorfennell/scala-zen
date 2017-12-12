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

### Add dependency

#### Purpose
Show how to add a jar dependency

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
librayDependencies += "ch.qos.logback" % "logback-classic" % "0.9.28"
```

`./HelloWorld.scala`
```Scala
object HelloWorld extends App {
    println("Hi Martin!")
}
```

##### Notes
- `libraryDependencies` is a `SettingsKey[Seq[ModuleId]]`, it comes prepopulated with `org.scala-lang:scala-library:2.12.4`.  
- `+=` adds a library dependency to the `Seq[ModuleID]` and sets it in the `SettingsKey`.  
- `"ch.qos.logback"` is automatically converted to a `GroupId` and has the function `%` which takes an artifact string `"logback-classic"` and returns a `GroupArtifact`.  
- Finally to make it a `ModuleId` it needs a version and this is added with the `%` function of the `GroupArtifact`  


### Cross build for different versions of scala

#### Purpose
Show how to cross build for different versions of Scala.

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
crossScalaVersions := Seq("2.11.2", "2.12.2")
```

`./HelloWorld.scala`
```Scala
object HelloWorld extends App {
    println("Hi Martin!")
}
```

#### SBT commands
```Bash
sbt "+ package"
```
`sbt "+ package"`  
- outputs a version `2.12` jar  `/target/scala-2.12/hello_2.12-1.0.1.jar`  
- outputs a version `2.11` jar `/target/scala-2.12/hello_2.11-1.0.1.jar`

### Prevent SBT including test dependencies
Show how adding a configuration `% test` to the `ModuleId` prevents it from being included when building a fat jar. 

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
librayDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
```

`./HelloWorld.scala`
```Scala
object HelloWorld extends App {
    println("Hi Martin!")
}
```

### Show how to run command line tools

#### Purpose
Determines the current git commit SHA"

#### File layout
```
./build.sbt
./HelloWorld.scala
```

#### Files
`./build.sbt`
```Scala
import scala.sys.process.Process

val head = taskKey[String]("Determines the current git commit SHA")
head := Process("git rev-parse head").lineStream.head
```

`./HelloWorld.scala`
```Scala
object HelloWorld extends App {
    println("Hi Martin!")
}
```

#### SBT commands
```Bash
sbt
head
```
`sbt`  
- loads up the `head` task defined in the build.sbt  
`head`  
- Runs the defined `head` task and assigns the first line result, this being the commit SHA  
