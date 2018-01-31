## SBT Examples
These examples presume you have SBT installed from http://www.scala-sbt.org/

### Sbt version manager  
There is very useful sbt version manager you can find here [sbtenv](https://github.com/sbtenv/sbtenv)  

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
sbt.version=1.1.0
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
To define custom tasks, the `taskKey` function is used to generate a `TaskKey[T]`.  
Then the actual code to be run can be set on the task using the `:=` function.  

#### Purpose
Determines the current git commit SHA

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

### Show details including version about sbt

#### Purpose
Shows the following:  
- version of sbt  
- what version of scala is built against  
- shows available plugins  

#### SBT commands
```Bash
sbt about
```
`sbt about`  
- Outputs `[info] This is sbt 1.0.3`  

### Set scalac options in sbt  

#### Purpose
Show how to set `scalac` options.  

#### File layout
```
./build.sbt
./HelloWorld.scala
```

#### Files
`./build.sbt`
```Scala
scalacOptions := Seq("-deprecation")
```

`./HelloWorld.scala`
```Scala
object Main extends App {
    @deprecated("remove this in method", "1.0.0")
    def hello() = println("Hello Martin")
    
    hello()
}
```

#### SBT commands
```Bash
sbt run
```
`sbt compile`  
-  While compiling it will warn on the deprecated function
```
[warn] /Users/conor/mess/sbt-examlpes/HelloWorld.scala:4:5: method hello in object Main is deprecated (since 1.0.0): remove this in method
[warn]     hello()
[warn]     ^
[warn] one warning found
```

### Use a plugin in sbt 

#### Purpose
Adds the scalastyle plugin to the project.

#### File layout
```
./build.sbt
./project/plugins.sbt
./src/main/scala/HelloWorld.scala
```

#### Files
`./project/plugins.sbt`
```
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")
```

`./build.sbt`
```Scala
scalastyleFailOnError := true
scalastyleFailOnWarning := true
```

`./src/main/scala/HelloWorld.scala`
```Scala
object Main extends App {
    @deprecated("remove this in method", "1.0.0")
    def hello() = println("Hello Martin")
    
    hello()
}
```

#### SBT commands
```Bash
sbt scalastyleGenerateConfig
sbt scalastyle
```
`sbt scalastyle`
- Checks the code for style
```
[warn] ./sbt-examlpes/src/main/scala/HelloWorld.scala:1: Header does not match expected text
[warn] ./sbt-examlpes/src/main/scala/HelloWorld.scala:2:4: Regular expression matched 'println'
[info] scalastyle Processed 1 file(s)
[info] scalastyle Found 0 errors
[info] scalastyle Found 2 warnings
[info] scalastyle Found 0 infos
[info] scalastyle Finished in 0 ms
```

### Show how to call tasks from other tasks

#### Purpose
Shows how a task can be ran by calling `.value` on it.

#### File layout
```
./build.sbt
```

#### Files

`./build.sbt`
```Scala
val copy = taskKey[Unit]("copy")
val delete = taskKey[Unit]("delete")
val combine = taskKey[Unit]("combine")
copy := println("copy")
delete := println("delete")
combine := {
    copy.value
    delete.value
    println("combine")
}
```

#### SBT commands
```Bash
sbt 
combine
```
`sbt combine`
- Runs copy and delete tasks
```
delete
copy
combine
```

### Show to log out to the sbt logger

#### Purpose
Rather than using println use the sbt logger to output to the console  

#### File layout
```
./build.sbt
```

#### Files
`./build.sbt`

#### Files
```Scala
val task = inputKey[Unit]("I am a task")

task := {
    val s = streams.value
    s.log.info("logged info")
    s.log.warn("logged warn")
    s.log.error("logged error")
}
```
#### SBT commands
```Bash
sbt print
```
`sbt print`
```
[info] logged info
[warn] logged warn
[error] logged error
```

### Show to take input into a task

#### Purpose
Show how to parse input correctly

#### File layout
```
./build.sbt
```

#### Files
`./build.sbt`
```Scala
import sbt.complete._
import complete.DefaultParsers._

val myTask = inputKey[Unit]("This task takes a parameter!")

val stageEnv: Parser[String] = " staging" 
val intEnv : Parser[String] = " integration" 
val prodEnv : Parser[String] = " production"
val combinedParser: Parser[String] = stageEnv | intEnv | prodEnv

myTask := {
    val environment = combinedParser.parsed.trim
    val s = streams.value
    s.log.info(s"Parameter was $environment")
}
```

#### SBT commands
```Bash
sbt myTask <something>
```
`sbt myTask staging`
- prints out staging
`sbt myTask blah`
```
[error] Expected end of input.
[error] Expected 'staging'
[error] Expected 'integration'
[error] Expected 'production'
[error] myTask blah
```

### Check the dependencies of a build.sbt

#### Purpose
A command to see all the dependenices that are being used

#### File layout
```
./build.sbt
```

#### Files
`./build.sbt`
```Scala
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
```

#### SBT commands
```Bash
sbt "inspect tree allDependencies"
```
```
[info] *:allDependencies = Task[scala.collection.Seq[sbt.librarymanagement.ModuleID]]
[info]   +-*:libraryDependencies = List(org.scala-lang:scala-library:2.12.4, org.scalatest:scalatest:3.0.4:test)
[info]   | +-*/*:autoScalaLibrary = true
[info]   | +-*/*:managedScalaInstance = true
[info]   | +-*/*:sbtPlugin = false
[info]   | +-*/*:scalaHome = None
[info]   | +-*/*:scalaOrganization = org.scala-lang
[info]   | +-*/*:scalaVersion = 2.12.4
[info]   | 
[info]   +-*/*:managedScalaInstance = true
[info]   +-*:pluginCrossBuild::sbtDependency = org.scala-sbt:sbt:1.0.4
[info]   | +-*/*:appConfiguration = xsbt.boot.AppConfiguration@531ed68e
[info]   | +-*/*:pluginCrossBuild::sbtVersion = 1.0.4
[info]   | +-*/*:scalaBinaryVersion = 2.12
[info]   | +-*/*:scalaVersion = 2.12.4
[info]   | 
[info]   +-*:projectDependencies = Task[scala.collection.Seq[sbt.librarymanagement.ModuleID]]
[info]   | +-*/*:buildDependencies = sbt.internal.BuildDependencies@551976c2
[info]   | +-*/*:settingsData = Task[sbt.internal.util.Settings[sbt.Scope]]
[info]   | +-*:thisProjectRef = ProjectRef(file:/Users/conor/mess/sbt-examlpes/,sbt-examlpes)
[info]   | 
[info]   +-*/*:sbtDependency = org.scala-sbt:sbt:1.0.4
[info]   +-*/*:sbtPlugin = false
[info]   +-*/*:scalaHome = None
[info]   +-*:scalaModuleInfo = Some(ScalaModuleInfo(2.12.4, 2.12, Vector(), true, false, true, org.scala-lang, Vector(scala-library, scala-compiler, scala-..
[info]   | +-*/*:scalaArtifacts = Vector(scala-library, scala-compiler, scala-reflect, scala-actors, scalap)
[info]   | +-*/*:scalaOrganization = org.scala-lang
[info]   | +-*/*:scalaBinaryVersion = 2.12
[info]   | +-*/*:scalaVersion = 2.12.4
[info]   | 
[info]   +-*/*:scalaOrganization = org.scala-lang
[info]   +-*/*:scalaVersion = 2.12.4
[info]   
```

### Show all the tasks defined for this project

#### SBT commands
```Bash
sbt "tasks -v"
```
```
  bgRun                           Start an application's default main class as a background job
  bgRunMain                       Start a provided main class as a background job
  clean                           Deletes files produced by the build, such as generated sources, compiled classes, and task caches.
  compile                         Compiles sources.
  console                         Starts the Scala interpreter with the project classes on the classpath.
  consoleProject                  Starts the Scala interpreter with the sbt and the build definition on the classpath and useful imports.
  consoleQuick                    Starts the Scala interpreter with the project dependencies on the classpath.
  copyResources                   Copies resources to the output directory.
  deliver                         Generates the Ivy file for publishing to a repository.
  deliverLocal                    Generates the Ivy file for publishing to the local repository.
  dependencyClasspath             The classpath consisting of internal and external, managed and unmanaged dependencies.
  dependencyClasspathAsJars       The classpath consisting of internal and external, managed and unmanaged dependencies, all as JARs.
  doc                             Generates API documentation.
  exportedProductJars             Build products that go on the exported classpath as JARs.
  exportedProductJarsIfMissing    Build products that go on the exported classpath as JARs if missing.
  exportedProductJarsNoTracking   Just the exported classpath as JARs without triggering the compilation.
  fullClasspath                   The exported classpath, consisting of build products and unmanaged and managed, internal and external dependencies.
  fullClasspathAsJars             The exported classpath, consisting of build products and unmanaged and managed, internal and external dependencies, all as JARs.
  internalDependencyAsJars        The internal (inter-project) classpath as JARs.
  javaOptions                     Options passed to a new JVM when forking.
  javacOptions                    Options for the Java compiler.
  mainClass                       Defines the main class for packaging or running.
  makePom                         Generates a pom for publishing when publishing Maven-style.
  manipulateBytecode              Manipulates generated bytecode
  mappings                        Defines the mappings from a file to a path, used by packaging, for example.
  package                         Produces the main artifact, such as a binary jar.  This is typically an alias for the task that actually does the packaging.
  packageBin                      Produces a main artifact, such as a binary jar.
  packageDoc                      Produces a documentation artifact, such as a jar containing API documentation.
  packageSrc                      Produces a source artifact, such as a jar containing sources and resources.
  printWarnings                   Shows warnings from compilation, including ones that weren't printed initially.
  publish                         Publishes artifacts to a repository.
  publishLocal                    Publishes artifacts to the local Ivy repository.
  publishM2                       Publishes artifacts to the local Maven repository.
  publishTo                       The resolver to publish to.
  publisher                       Provides the sbt interface to publisher
  run                             Runs a main class, passing along arguments provided on the command line.
  runMain                         Runs the main class selected by the first argument, passing the remaining arguments to the main method.
  scalacOptions                   Options for the Scala compiler.
  test                            Executes all tests.
  testOnly                        Executes the tests provided as arguments or all tests if no arguments are provided.
  testOptions                     Options for running tests.
  testQuick                       Executes the tests that either failed before, were not run or whose transitive dependencies changed, among those provided as arguments.
  unmanagedClasspath              Classpath entries (deep) that are manually managed.
  unmanagedJars                   Classpath entries for the current project (shallow) that are manually managed.
  unmanagedResources              Unmanaged resources, which are manually created.
  unmanagedSources                Unmanaged sources, which are manually created.
  update                          Resolves and optionally retrieves dependencies, producing a report.
  updateClassifiers               Resolves and optionally retrieves classified artifacts, such as javadocs and sources, for dependency definitions, transitively.
  updateSbtClassifiers            Resolves and optionally retrieves classifiers, such as javadocs and sources, for sbt, transitively.
  woo                             Woo
```

### Show how to rerun commands when code changes
By putting `~` before any command it puts sbt in a mode that reruns the last command

#### SBT commands
```Bash
sbt ~compile
```
```
1. Waiting for source changes... (press enter to interrupt)
```

### Configure how to run tasks in a seperate JVM
Setting the property `fork` to true will kick it off in a new JVM

#### File layout
```
./build.sbt
```

#### Files
`./build.sbt`
```Scala
fork := true
```

### Use older version of SBT through global sbt config file  

#### File layout  
```
/usr/local/etc/sbtopts
```

#### Files
`/usr/local/etc/sbtopts`
```Scala
# Sets the SBT version to use.
-sbt-version  0.13.12
```

Other options  
```
# Disable ANSI color codes
#
#-no-colors

# Starts sbt even if the current directory contains no sbt project.
#
-sbt-create

# Path to global settings/plugins directory (default: ~/.sbt)
#
#-sbt-dir  /etc/sbt

# Path to shared boot directory (default: ~/.sbt/boot in 0.11 series)
#
#-sbt-boot ~/.sbt/boot  

# Path to local Ivy repository (default: ~/.ivy2)
#
#-ivy ~/.ivy2

# set memory options
#
#-mem   <integer>  

# Use local caches for projects, no sharing.
#
#-no-share

# Put SBT in offline mode.
#
#-offline

# Sets the SBT version to use.
#-sbt-version  0.11.3

# Scala version (default: latest release)
#
#-scala-home <path>        
#-scala-version <version>

# java version (default: java from PATH, currently $(java -version |& grep version))
#
#-java-home <path>
```
