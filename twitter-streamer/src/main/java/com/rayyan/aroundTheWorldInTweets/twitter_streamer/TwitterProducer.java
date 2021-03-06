package com.rayyan.aroundTheWorldInTweets.twitter_streamer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rayyan.aroundTheWorldInTweets.twitter_streamer.utils.KafkaUtils;
import com.rayyan.aroundTheWorldInTweets.twitter_streamer.utils.TwitterUtils;
import com.twitter.hbc.core.Client;

public class TwitterProducer {
	
	Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());
	
	public void startReceivingTweets() {
		logger.info("Starting to receive tweets");
		
		TwitterUtils tu = new TwitterUtils();
		KafkaUtils ku = new KafkaUtils();
		
		//Create a twitter client
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100);
		Client client = tu.createTwitterClient(msgQueue);
		client.connect();
		
		//Create a Kafka producer
		KafkaProducer<String, String> kafkaProducer = ku.createKafkaProducer();
		
		//Send message to Kafka and Logger
		String msg = null;
		while (!client.isDone()) {
			  
			try {
				msg = msgQueue.poll(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				client.stop();
			}
			if(msg != null) {
				logger.info(extractText(msg));
				kafkaProducer.send(new ProducerRecord<String, String>("twitter-tweets", extractText(msg)));
			}
		}
		
		logger.info("End of application");
	}
	
	public String extractText(String message) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(message);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (String) jsonObject.get("text");
	}

}
