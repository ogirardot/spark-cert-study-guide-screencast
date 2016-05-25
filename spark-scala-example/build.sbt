name := "spark-scala-test"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.3.1" //% "provided"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.3.1" //% "provided"

libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.3.1" //% "provided"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.3.1" //% "provided"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.3.1" //% "provided"

resolvers += Resolver.mavenLocal

resolvers += Resolver.typesafeRepo("releases")

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("public")

test in assembly := {}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
