import org.apache.spark.SparkContext

object Main extends App {
  val sc = new SparkContext("local", "Spark Core App")

  val rdd = sc.parallelize(List("I am a parallel program", "I am too", "Don't forget me !"))
  val localResult = rdd
    .flatMap(line => line.split(" "))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
    .collect()

  // Local Scala STD API
  localResult.foreach(println)
  sc.stop()
}