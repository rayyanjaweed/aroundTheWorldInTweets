package com.rayyan.aroundTheWorldInTweets.twitter_streamer.utils;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaUtils {
	
	private String bootstrapServers = "localhost:9092";
	
	private KafkaProducer<String, String> createKafkaProducer() {
		
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
		
		return kafkaProducer;
	}

}
