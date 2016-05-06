package edu.umkc

import edu.umkc.ic.IPSettings
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by Venu on 7/28/15.
 */
object SparkTest {

  def main(args: Array[String]) {

    val conf = new SparkConf()
      .setAppName(s"IPApp")
      .setMaster("local[*]")
      .set("spark.executor.memory", "2g")
    val ssc = new StreamingContext(conf, Seconds(2))
    val sc = ssc.sparkContext

    val images = sc.wholeTextFiles(s"${IPSettings.INPUT_DIR}/*/*.jpg").cache()




  }
}
