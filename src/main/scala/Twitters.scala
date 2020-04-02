import java.util.concurrent.LinkedBlockingQueue

import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener, Twitter, TwitterFactory, TwitterStream, TwitterStreamFactory}

case class Twitters(account: Account, keywords: String*) {

  lazy val queue: LinkedBlockingQueue[Status] = new LinkedBlockingQueue[Status](1000)
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

  lazy val tweets: Twitter = new TwitterFactory(account.getConfiguration.build).getInstance()
//    .addListener(listener)
}