package com.utad.bigdata.sparkdemo

import java.io.PrintStream
import java.net.ServerSocket

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.BufferedSource

/**
  * Aplicación para lanzar mediante un Spark-Submit.
  * http://spark.apache.org/docs/latest/submitting-applications.html
  * Esta aplicación es un servidor de sockets muy básico para recibir líneas de texto
  * y devolver un análisis de la aparición del número de vocales.
  * Más información sobre sockets: https://es.wikipedia.org/wiki/Socket_de_Internet
  */
object VowelsCounter extends App {

  // Aunque aquí no se provee el Master en la Configuración,
  // se proveerá posteriormente al hacer Spark-Submit.
  val sparkConf = new SparkConf().setAppName("Utad Vowels counter")
  val sc = new SparkContext(sparkConf)

  // El servidor escuchará peticiones y recibirá el texto en el puerto 9070
  val server = new ServerSocket(9070)

  while(true){

    val s = server.accept()

    // Canal de comunicación con datos entrantes (petición)
    val in = new BufferedSource(s.getInputStream()).getLines()
    // Canal de comunicación con datos salientes (respuesta)
    val out = new PrintStream(s.getOutputStream())

    in.foreach { l =>
      val resultRDD = sc.parallelize(l.split(" ")).
        flatMap(word => word.toLowerCase.toCharArray).
        filter(Set('a', 'e', 'i', 'o', 'u').contains).
        map((_, 1)).
        reduceByKey(_ + _)
        .map(e => s"${e._1} - ${e._2}").cache()

      // mkString convierte una colección en texto
      val result = resultRDD.collect().mkString("; ")

      // Respondemos con el resultado del análisis
      out.println(result)
      out.flush()
    }

    s.close()

  }

  sc.stop()

}
