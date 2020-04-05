import org.apache.kafka.clients.producer._
import java.util.Properties
import java.util.ArrayList

import twitter4j.JSONObject

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

object KafkaProducer extends App {

  val m = Map("bootstrap.servers"->"localhost:9092", "acks"->"all", "key.serializer"->"org.apache.kafka.common.serialization.StringSerializer", "value.serializer"->"org.apache.kafka.common.serialization.StringSerializer", "request.timeout.ms"->"60000")

  val kfk = Kafka(m)
  val account = Account("f6sxSv3IkOnEyNIwF9Ycayf6i", "mvVQNtYGDDe5Tv3T3Wwa5SL1GYaqY9PFow91m4jSs0t7bbtltV", "3166578447-hoMCQrwmdoeJRj14aoPIwj2G7hWINgdvAmkOv9F", "FndXsZ7REb8PekqUZMwaxRtHU2ubj3W8Xk9RmoaPGyWsV")
  val twitter = Twitters(account)

  twitter.searchN(5, "coronavirus")
  val ResultToJson: ListBuffer[JSONObject] = twitter.toJson

  kfk.sendList("tweets_of_Coronavirus", ResultToJson.toList)
  kfk.close

}
