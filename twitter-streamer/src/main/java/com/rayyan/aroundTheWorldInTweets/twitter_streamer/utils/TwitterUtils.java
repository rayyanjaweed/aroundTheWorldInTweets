package com.rayyan.aroundTheWorldInTweets.twitter_streamer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterUtils {
	
	Logger logger = LoggerFactory.getLogger(TwitterUtils.class.getName());
	
	public static void main(String[] args) {
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100);
		TwitterUtils tu = new TwitterUtils();
		tu.createTwitterClient(msgQueue);
	}
	
	public Client createTwitterClient(BlockingQueue<String> msgQueue) {
		// HARDCODING THIS VALUE
		// HARDCODING THIS VALUE
		// Delete Later
		List<String> terms = Lists.newArrayList("Game of thrones","#GOT","Cersei","Khaleesi");
		// HARDCODING THIS VALUE
		// HARDCODING THIS VALUE
		
		Properties properties = new Properties();
		InputStream stream = getClass().getClassLoader().getResourceAsStream("secret.properties");
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception while trying to read secret properties", e);
		}
		
		String consumerKey = properties.getProperty("consumerKey");
		String consumerSecret = properties.getProperty("consumerSecret");
		String token = properties.getProperty("token");
		String secret = properties.getProperty("secret");
		
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		hosebirdEndpoint.trackTerms(terms);

		// These secrets should be read from a config file
		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		ClientBuilder builder = new ClientBuilder()
				  .name("Hosebird-Client-01")
				  .hosts(hosebirdHosts)
				  .authentication(hosebirdAuth)
				  .endpoint(hosebirdEndpoint)
				  .processor(new StringDelimitedProcessor(msgQueue));

		Client hosebirdClient = builder.build();
		
		
		return hosebirdClient;
	}

}
