import org.apache.kafka.clients.producer._
import java.util.Properties
import java.util.ArrayList

import twitter4j.{Query, QueryResult, Status, TwitterException}

object KafkaProducer extends App {

  val prop = new Properties
  prop.put("bootstrap.servers", "localhost:9092")
  prop.put("acks", "all")
  //prop.put("retries", "3")
  prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  prop.put("request.timeout.ms", "60000")
  //prop.put("batch.size", "16384")
  //prop.put("linger.ms", "1")
  //prop.put("buffer.memory", "33554432")

  // 得到生产者的实例
  val producer = new KafkaProducer[String, String](prop)

  val account = Account("", "", "", "")
  val twitter = Twitters(account, "")
  // 模拟一些数据并发送给kafka
//  for (i <- 1 to 100) {
//    val msg = s"${i}: this is a linys ${i} kafka data"
//    println("send -->" + msg)
//    // 得到返回值
//    val rmd: RecordMetadata = producer.send(new ProducerRecord[String, String]("linys", msg)).get()
//    println(rmd.toString)
//    Thread.sleep(500)
//  }
  val query = new Query(twitter.keywords)
  val numberOfTweets = 5000;
  var lastID = Long.MaxValue;
  val tweets = new ArrayList[Status]();
  while (tweets.size () < numberOfTweets) {
    if (numberOfTweets - tweets.size() > 100)
      query.setCount(100);
    else
      query.setCount(numberOfTweets - tweets.size());
    try {
      val result = twitter.tweets.search(query)
      tweets.addAll(result.getTweets());
      print("Gathered " + tweets.size() + " tweets" + "\n");
      tweets.forEach(tweet => if (tweet.getId < lastID) lastID = tweet.getUser)
    }

    catch {
      case e: TwitterException => {
        System.err.println("Couldn't connect: " + e)
        e.printStackTrace()
      }

        query.setMaxId(lastID - 1);
    }
  }
//   tweet.getUser.getStatus.getText

  producer.close()

}
