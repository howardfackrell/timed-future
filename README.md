# Timed Futures
## in Scala

This project shows how it is possible to create custom behavior on futures.  The implementation
prints the time to run the future, but the pattern could be used to do other things as well.

### Some code

This is the core, it creates a Future but adds in some additional functionality

```scala
    object TimedFuture {
      def apply[A](logger : Long => Unit)(f : => A)(implicit context : ExecutionContext) : Future[A] = {
        Future {
          val start = new Date
          val result = f
          logger(new Date().getTime - start.getTime)
          result
        }
      }
    }
        def log(description : String) : Long => Unit = {
          (elapsedMillis : Long) => println(s"$description completed in $elapsedMillis milliseconds")
        }
```

Usage Looks like this:

```scala
    val one : Future[Int] = TimedFuture(log("first")) {
      Thread.sleep(1000)
      1
    }
```


Of course, You can also supply your own function to handle what to do with the duration of the future:

```scala
    val three : Future[Int] = TimedFuture(println _) {
      Thread.sleep(3000)
      3
    }
```
