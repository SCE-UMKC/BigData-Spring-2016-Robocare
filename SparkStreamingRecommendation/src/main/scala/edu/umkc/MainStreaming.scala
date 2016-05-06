import java.io.{File, ByteArrayInputStream}
import java.net.InetAddress
import javax.imageio.ImageIO

import com.sun.jersey.core.util.Base64
import edu.umkc.ic.{IPApp, IPSettings}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import sun.misc.BASE64Decoder

import edu.umkc._

/**
 * Created by Mayanka on 23-Jul-15.
 */
object MainStreaming {
  def main (args: Array[String]) {
    System.setProperty("hadoop.home.dir","F:\\winutils")
    val sparkConf=new SparkConf()
      .setAppName("SparkStreaming")
      .set("spark.executor.memory", "4g").setMaster("local[*]")

    val sparkConf1=new SparkConf()
      .setAppName("SparkStreaming1")
      .set("spark.executor.memory", "2g").setMaster("local[*]")
    val ssc= new StreamingContext(sparkConf,Seconds(2))
   // val ssc1= new StreamingContext(sparkConf1,Seconds(10))
    val sc=ssc.sparkContext

  //  val sc1 = ssc1.sparkContext

    val ip=InetAddress.getByName("10.205.0.219").getHostName
    val lines=ssc.socketTextStream(ip,1234)

    val linesData = lines.cache()

   val command= linesData.map(x=>{
      val y=x.toUpperCase()
      y
    })
    command.foreachRDD(
    rdd=>
      {
        if(rdd.collect().contains("RECOMMEND"))
        {
          Recommendation.recommend(rdd.context)
        }
        else if(rdd.collect().contains("SENTIMENT")){
          val sent = new SentimentAnalyzer()

          println("sentiment analysis")

          val output = sent.findSentiment("I am very happy")

          iOSConnector.sendCommandToRobot("sentiment analysis :::"+output.toString)

          println("output"+output)
       }
 else {

//          println("inside the classify block")
//
//          val images = sc.wholeTextFiles(s"${IPSettings.INPUT_DIR}/*/*.jpg").cache()
//
//          IPApp.extractDescriptors(sc,images)
//
//          IPApp.kMeansCluster(sc)
//
//          IPApp.createHistogram(sc,images)
//
//          IPApp.generateNaiveBayesModel(sc)
//
//          println("Hello inside after this")
//
//
//
//          val ip = InetAddress.getByName("10.205.0.7").getHostName
//
//          //    val lines = ssc.receiverStream(new CustomReceiver(ip,5555))
//          val lines = ssc.socketTextStream(ip, 5555)

          val data = linesData.map(line => {
            line
          })

          data.print()

          //Filtering out the non base64 strings
          val base64Strings = lines.filter(line => {
            Base64.isBase64(line)
          })

          base64Strings.foreachRDD(rdd => {
            val base64s = rdd.collect()
            for (base64 <- base64s) {
              val bufferedImage = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64)))
              val imgOutFile = new File("newLabel.jpg")
              val saved = ImageIO.write(bufferedImage, "jpg", imgOutFile)
              println("Saved : " + saved)

              if (saved) {
                val category = IPApp.classifyImage(rdd.context, "newLabel.jpg")
                println(category)

                iOSConnector.sendCommandToRobot(category.toString)
              }
            }
          })

        }

//        else{
//          println("inside else block")
//          rdd.collect().foreach(x=>iOSConnector.sendCommandToRobot(x.toString))
//
//
//         // iOSConnector.sendCommandToRobot(rdd.collect().toString)
//        }
      }
    )
    lines.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
