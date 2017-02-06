# SparkSubmitDemo

**NOTA: Esta aplicación está creada y debe ser lanzada con la versión 2.1 de Spark.**

**NOTA: $SPARK_HOME se refirirá al directorio donde se descomprimió el fichero tar.gz de Spark.**

**NOTA: $SPARK_MASTER_URL dependerá de si se desplegó el cluster de Spark 
      en modo standalone (Ej.: spark://localhost:7077) o con mesos (Ej.: mesos://localhost:5050)**

**NOTA: Para lanzar esta aplicación en modo cluster, ya debe haber un Spark cluster desplegado 
en modo standalone o con Mesos.**

Para lanzar el servidor de contador de vocales se deben seguir los siguientes pasos, desde un terminal:

- mvn clean install
- cp target/2.11/demospark-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp/
- cd $SPARK_HOME
- bin/spark-submit --class com.utad.bigdata.sparkdemo.ContadorDeVocales --master $SPARK_MASTER_URL --deploy-mode cluster /tmp/demospark-1.0-SNAPSHOT-jar-with-dependencies.jar

Una vez hecho esto, la aplicación estará ejecutándose dentro del cluster de Spark.

Para enviar peticiones al servidor, basta con ejecutar el siguiente comando en el terminal:

- nc localhost 9070

A partir de ahí, se puede enviar texto y el servidor responderá con el resultado. Para salir Control+C.
