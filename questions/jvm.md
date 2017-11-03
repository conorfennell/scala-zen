## Java Virtual Machine Questions

1. [What is strong soft weak and phantom references](#what-is-strong-soft-weak-and-phantom-references)

## What is strong soft weak and phantom references
Going from strongest to weakest, the different levels of memory resilience reflect the life cycle of an object.  

`strong` when there is a reference in scope it will not be garbaged collected.  

`soft` always eligible for garbage collection, it resists GC cycle until memory is available and there is no risk of OutOfMemoryError (in that case, it can be removed).  

`weak` always eligible for garbage collection, it depends on the jvm if retain it or not.  

`phantom` This reference type differs from the other types defined in scala.ref Package because it isn't meant to be used to access the object, but as a signal that the object has already been finalized, and the garbage collector is ready to reclaim its memory.

```Scala

import scala.ref.SoftReference
import scala.ref.WeakReference
import scala.ref.PhantomReference
import scala.ref.ReferenceQueue

case class A(a: Int)


val strongReference = A(1)
var anotherStrongReference = A(1)


val softReference = SoftReference(A(4))

softReference.get match {
    case Some(A(value)) => println("Enough memory so not garbage collected")
    case None =>  println("Near running out of memory so was garbage collected")
}

val weakReference = WeakReference(A(4))

weakReference.get match {
    case Some(A(value)) => println("Garbage collection has not yet run so still active")
    case None =>  println("Garbage collection ran and has garbage collected")
}


val referenceQueue = new ReferenceQueue()
val phantomReference = new PhantomReference(A(2), referenceQueue)
// Later on another point
val reference = referenceQueue.remove
// Now, clear up any thing you want

```