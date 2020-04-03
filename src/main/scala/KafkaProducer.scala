import org.apache.kafka.clients.producer._
import java.util.Properties
import java.util.ArrayList

import twitter4j.JSONObject

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

object KafkaProducer extends App {

  val m = Map("bootstrap.servers"->"localhost:9092", "acks"->"all", "key.serializer"->"org.apache.kafka.common.serialization.StringSerializer", "value.serializer"->"org.apache.kafka.common.serialization.StringSerializer", "request.timeout.ms"->"60000")

  val kfk = Kafka(m)
  val producer = kfk.producer
  val account = Account("", "", "", "")
  val twitter = Twitters(account, "")

  twitter.searchN(1)
  val ResultToJson: ListBuffer[JSONObject] = twitter.toJson

  for (tweet <- ResultToJson) {
    println("send -->" + tweet)
    // 得到返回值
    val rmd: RecordMetadata = producer.send(new ProducerRecord[String, String]("twitter", tweet.toString)).get()
    println(rmd.toString)
    Thread.sleep(500)
  }

  producer.close()

}
