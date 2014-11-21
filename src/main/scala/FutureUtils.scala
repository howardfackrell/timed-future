import java.util.Date
import scala.concurrent.Future
import scala.concurrent._

/**
 * Created by howard.fackrell on 11/21/14.
 */
package object FutureUtils {

  def timedFuture[A] (logger : Long => Unit)(f : => A)(implicit context : ExecutionContext) : Future[A] = {
    TimedFuture(logger)(f)
  }

  def log(description : String) : Long => Unit = {
    (elapsedMillis : Long) => println(s"$description completed in $elapsedMillis milliseconds")
  }

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
}
