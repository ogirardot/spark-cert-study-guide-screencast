import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext

object SQLMain extends App {
  val sc = new SparkContext("local", "Spark SQL App")

  val sqlContext = new SQLContext(sc)
  val path = "~/Working Files/Chapter 5/data_titanic.json"
  val df = sqlContext.load(path, "json")

  df.registerTempTable("passengers")

  val stats = sqlContext.sql(
    """
      |SELECT sex,
      | (SUM(age) / count(*)) AS mean_age
      | , MIN(age) as youngest
      | , MAX(age) as oldest
      | FROM passengers GROUP BY sex
    """.stripMargin)

  sqlContext.cacheTable("passengers")

  sqlContext.udf.register("first_letter",
    (input: String) => input.charAt(0).toString)

  sqlContext
    .sql("SELECT first_letter(name) from passengers")
    .show()

  df.printSchema()
  df.show()
}