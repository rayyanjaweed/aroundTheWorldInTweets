package com.rayyan.aroundTheWorldInTweets.twitter_streamer;

/**
 * Hello world!
 *
 */
public class StreamerApp 
{
    public static void main( String[] args )
    {
    	TwitterProducer tp = new TwitterProducer();
    	tp.startReceivingTweets();
    }
}
