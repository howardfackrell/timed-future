
import java.util.Date
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import FutureUtils._
import scala.concurrent.ExecutionContext.Implicits.global

object TimedFutureRunner {

  def main(args: Array[String]): Unit = {

    println("start " + new Date)

    val one : Future[Int] = TimedFuture(log("first")) {
      Thread.sleep(1000)
      1
    }

    val two : Future[Int] = TimedFuture(log("second")) {
      Thread.sleep(2000)
      2
    }

    val three : Future[Int] = TimedFuture(println _) {
      Thread.sleep(3000)
      3
    }

    println("Done")

    val total = for (o <- one; t <- two; th <- three) yield { o + t + th}

    val result = Await.result(total, Duration(30, TimeUnit.SECONDS))
    println(s"Really Done, result is $result")
    println(s"end $result " + new Date)
  }

}
