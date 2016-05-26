import org.apache.spark.SparkContext
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors

object KmeansApp extends App {
  val sc = new SparkContext("local", "KMeans App")
  val path = "~/Working Files/Chapter 6/iris.data"

  val data = sc.textFile(path)
    .map { line =>
      Vectors.dense(
        line.split(",").slice(0, 3).map(_.toDouble)
      )
    }

  val computedCenters = KMeans.train(data, k = 3, maxIterations = 200)
  val WSSE = computedCenters.computeCost(data)
  println(s"Within Set Sum Error $WSSE")

  sc.stop()
}