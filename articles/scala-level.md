# Scala levels: beginner to expert, application programmer to library designer
#### by the creator Martin Oderskey
Scala is a bit of a chameleon. It makes many programming tasks refreshingly easy and at the same time contains some pretty intricate constructs that allow experts to design truly advanced typesafe libraries. This means that, depending at what piece of code you look at, Scala might look very simple or very complex. But which pieces exactly are easy for beginners and which are more advanced?  In the end, everybody will have their own yardstick. Nevertheless, I am trying here to give a rough categorization.
I hope this will help newcomers to the language decide in what order to pick subjects to learn, and that it will give some advice to teachers and book authors in what order to present the material.
I assume that programmers have already a good knowledge of Java, so we can take at least pre-generics Java 1.4 for granted. If that's not the case, some of the entry-level concepts such as classes and exceptions would need to be moved to more advanced levels.

Also, I distinguish between Scala application programmers and Scala library designers, because the required skill sets are really quite different. A first shot at a categorization has been discussed last month on the scala-debate mailing list. The current categorization incorporates some of the suggestions that were made then. So, here we go:

## Level A1: Beginning application programmer
* Java-like statements and expressions: standard operators, method calls, conditionals, loops, try/catch
* class, object, def, val, var, import, package
* Infix notation for method calls
* Simple closures
* Collections with map, filter, etc
* for-expressions
 
## Level A2: Intermediate application programmer
* Pattern matching
* Trait composition
* Recursion, in particular tail recursion
* XML literals

## Level A3: Expert application programmer
* Folds, i.e. methods such as foldLeft, foldRight
* Streams and other lazy data structures
* Actors
* Combinator parsers

## Level L1: Junior library designer
* Type parameters
* Traits
* Lazy vals
* Control abstraction, currying
* By-name parameters

## Level L2: Senior library designer
* Variance annotations
* Existential types (e.g., to interface with Java wildcards)
* Self type annotations and the cake pattern for dependency injection
* Structural types (aka static duck typing)
* Defining map/flatmap/withFilter for new kinds of for-expressions
* Extractors

## Level L3: Expert library designer
* Early initializers
* Abstract types
* Implicit definitions
* Higher-kinded types

As I wrote above, the skill sets required from application programmers and library designers are really quite different. But if I should throw them into the same baskets, I would group like this:

* A1, A2/L1, A3/L2, L3

That is, intermediate application programming is about on the same level of difficulty as junior library design, and advanced application programming is on the same level as senior library design.

To clarify: One can program very productively in Scala on level A1, which one should be able to pick up in a day or so, coming from Java. Mastering A2 will doubtlessly increase programmer productivity. A3 is for expert programmers with more specialized tasks, not everyone needs to get to that level. The same holds for library design. There are great libraries designed with the tools on level L1 and L2. Some libraries require L3 elements such as implicits and higher-kinded types, but a library does not automatically get better if it uses these elements -- often the opposite is true.

[Source Blog](http://www.scala-lang.org/old/node/8610)