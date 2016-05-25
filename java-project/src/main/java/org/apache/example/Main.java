package org.apache.example;


import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext("local", "App");
        ArrayList<String> book = new ArrayList<String>();
        book.add("I am writing");
        book.add("I am here");
        book.add("I am, therefor I am");
        JavaRDD<String> rdd = sc.parallelize(book, 5);
        rdd.flatMap(line -> Arrays.asList(line.split(" ")))
                .mapToPair(word -> new Tuple2<String, Integer>(word, 1))
                .reduceByKey((current, accumulator) -> current + accumulator)
                .saveAsTextFile("data/output");
        //System.out.println(pairs);

    }
}
