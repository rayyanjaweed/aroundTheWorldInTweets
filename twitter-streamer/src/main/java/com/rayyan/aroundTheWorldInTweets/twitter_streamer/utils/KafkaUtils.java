package com.rayyan.aroundTheWorldInTweets.twitter_streamer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaUtils {
	
	Logger logger = LoggerFactory.getLogger(KafkaUtils.class.getName());
	
	public KafkaProducer<String, String> createKafkaProducer() {
		
		Properties properties = new Properties();
		InputStream stream = getClass().getClassLoader().getResourceAsStream("kafka.properties");
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception while trying to read kafka properties", e);
		}
		
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
		
		return kafkaProducer;
	}

}
