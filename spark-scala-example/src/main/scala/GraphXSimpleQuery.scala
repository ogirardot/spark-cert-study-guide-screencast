import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph}

object GraphXSimpleQuery extends App {
  val sc = new SparkContext("local", "GraphX App")

  val vertices = sc.parallelize(Array(
    (1L, "John"),
    (2L, "Maria"),
    (3L, "Patrick"),
    (4L, "Maurice")
  ))

  val edges = sc.parallelize(Array(
    Edge(1L, 2L, "loves"),
    Edge(1L, 3L, "hates"),
    Edge(2L, 3L, "loves"),
    Edge(4L, 2L, "loves")
  ))

  val graph = Graph(vertices, edges)

  graph.triplets.map { triplet =>
    s"${triplet.srcAttr} ${triplet.attr} ${triplet.dstAttr}"
  }.foreach(println)

  sc.stop()
}