import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer

case class Kafka() {
  
  val prop: Properties = new Properties
  lazy val producer: KafkaProducer[String, String] =  new KafkaProducer[String, String](prop)
}

object Kafka {
  def apply(m: Map[String, String]): Kafka = new Kafka() {
    m.foreach(p => prop.setProperty(p._1, p._2))
  }
}
