import java.util.concurrent.LinkedBlockingQueue

import twitter4j.{JSONObject, Query, StallWarning, Status, StatusDeletionNotice, StatusListener, Twitter, TwitterFactory, TwitterStream, TwitterStreamFactory}

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

case class Twitters(account: Account) {

  lazy val queue: LinkedBlockingQueue[Status] = new LinkedBlockingQueue[Status](1000)
  lazy val tweets: Twitter = new TwitterFactory(account.getConfiguration.build).getInstance
  lazy val searchResult = ListBuffer[Status]()

  def searchN(n: Int = 1, hashtag: String) = {
    val query = new Query(s"#$hashtag")
    val numberOfTweets = n
    var lastID = Long.MaxValue
    while (searchResult.length < numberOfTweets) {
      if (numberOfTweets - searchResult.length > 100)
        query.setCount(100);
      else
        query.setCount(numberOfTweets - searchResult.length)
      val response = Try(tweets.search(query))
      response match {
        case Failure(e) => {
          System.err.println("Couldn't connect: " + e)
          e.printStackTrace()
        }
        case Success(t) => {
          searchResult ++= t.getTweets.asScala
          print("Gathered " + searchResult.length + " tweets" + "\n");
        }
      }
    }
  }

  def dropAll = searchResult.clear
  def dropN = ???
  def toJson = {
    val total: ListBuffer[JSONObject] = ListBuffer[JSONObject]()
    for (t <- searchResult) {
      val tObject = new JSONObject()
      tObject.put("userId", t.getId)
        .put("userScreenName", t.getUser.getScreenName)
        .put("userName", t.getUser.getName)
        .put("timestamp", t.getCreatedAt)
        .put("favorite", t.getFavoriteCount)
        .put("location", t.getGeoLocation)
        .put("retweet", t.getRetweetCount)
        .put("hashtag", t.getHashtagEntities.toString)
        .put("all", t.toString)
      total.addOne(tObject)
    }
    total
  }

  def filter = ??? //keywords, and, or, without


//  lazy val listener: StatusListener = new StatusListener() {
//
//    @Override
//    def onStatus(status : Status) {
//      queue.offer(status);
//
//      // System.out.println("@" &plus; status.getUser().getScreenName() &plus; " - " &plus; status.getText());
//      // System.out.println("@" &plus; status.getUser().getScreen-Name());
//
//      /*for(URLEntity urle : status.getURLEntities()) {
//         System.out.println(urle.getDisplayURL());
//      }*/
//
//      /*for(HashtagEntity hashtage : status.getHashtagEntities()) {
//         System.out.println(hashtage.getText());
//      }*/
//    }
//
//    @Override
//    def onDeletionNotice( statusDeletionNotice:  StatusDeletionNotice) {
//      // System.out.println("Got a status deletion notice id:" &plus; statusDeletionNotice.getStatusId());
//    }
//
//    @Override
//    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
//      // System.out.println("Got track limitation notice:" &plus; num-berOfLimitedStatuses);
//    }
//
//    @Override
//    def onScrubGeo(userId: Long, upToStatusId: Long) {
//      // System.out.println("Got scrub_geo event userId:" &plus; userId &plus; "upToStatusId:" &plus; upToStatusId);
//    }
//
//    @Override
//    def onStallWarning(warning: StallWarning) {
//      // System.out.println("Got stall warning:" &plus; warning);
//    }
//
//    @Override
//    def onException(ex: Exception) {
//      ex.printStackTrace();
//    }
//  }


//    .addListener(listener)
}