import org.apache.kafka.clients.producer._
import java.util.Properties


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

  // 模拟一些数据并发送给kafka
  for (i <- 1 to 100) {
    val msg = s"${i}: this is a linys ${i} kafka data"
    println("send -->" + msg)
    // 得到返回值
    val rmd: RecordMetadata = producer.send(new ProducerRecord[String, String]("linys", msg)).get()
    println(rmd.toString)
    Thread.sleep(500)
  }

  producer.close()

}
