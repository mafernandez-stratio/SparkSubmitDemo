package com.utad.bigdata.sparkdemo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Esta aplicación es un ejemplo de como usar la API de Spark-core para realizar análisis.
  * En este caso, analizamos el número de veces que aparece la palabra Quijote en el libro.
  */
object Quijote extends App {

  // Configuración de Spark: http://spark.apache.org/docs/latest/configuration.html
  // Al menos, hay que proveer 2 propiedades: Master y Nombre de la aplicación.
  val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Utad Quijote")

  val sc = new SparkContext(sparkConf)

  // Conseguimos el path del fichero que está en la carpeta resources
  val quijotePath = getClass.getResource("/quijote.txt").getPath

  val rddQuijote = sc.textFile(quijotePath).cache

  val result = rddQuijote.flatMap(_.split(" ")).filter(_.equalsIgnoreCase("Quijote")).count

  // Mostramos el resultado por pantalla
  println(s"Quijote count: $result")

  // Antes de finalizar la aplicación, debemos parar el contexto para liberar los recursos
  sc.stop()

}
