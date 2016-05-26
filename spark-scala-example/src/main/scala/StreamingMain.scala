import org.apache.spark.SparkContext
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SimpleStreamingMain extends App {
  val sc = new SparkContext("local[2]", "Streaming App")

  import org.apache.spark.streaming._

  val ssc = new StreamingContext(sc, Seconds(2))
  val stream = ssc.socketTextStream("localhost", 9999) // to make it work launch *nc -lk 9999* on a *nix machine
  stream.map(line => (line, line.length)).print()

  ssc.start()
  ssc.awaitTermination()
  sc.stop()
}

object MutableStateStreamingAppWithCheckpoint extends App {

  def addLengthToState(values: Seq[Int], state: Option[Int]) = {
    Some(values.sum + state.getOrElse(0))
  }

  def createSparkStreamingContext() = {
    println("Creating a brand new context...")
    val sc = new SparkContext("local[2]", "Streaming App")
    import org.apache.spark.streaming._
    val context = new StreamingContext(sc, Seconds(2))
    context.checkpoint("check")

    val stream = context.socketTextStream("localhost", 9999) // to make it work launch *nc -lk 9999* on a *nix machine
    stream
      .map(line => (line, line.length))
      .updateStateByKey(addLengthToState)
      .print()
    context
  }

  val ssc = StreamingContext.getOrCreate("check", () => createSparkStreamingContext())
  ssc.start()
  ssc.awaitTermination()
  sc.stop()
}