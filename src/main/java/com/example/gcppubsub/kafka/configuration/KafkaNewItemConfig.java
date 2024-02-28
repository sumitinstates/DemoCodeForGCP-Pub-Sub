package com.example.gcppubsub.kafka.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaNewItemConfig {

	@Value("${newItems.bootstrap.servers}")
	private String newItemsbootStrapServers;
	
	@Value("${newItems.acks}")
	private String newItemsAcks;
	
	@Value("${newItems.retries}")
	private String newItemsRetries;
	
	@Value("${key.serializer}")
	private String keySerializer;
	
	@Value("${value.serializer}")
	private String valueSerializer;
	
	@Value("${newItem.publish.topic.name}")
	private String newItemsPublishTopicName;

	public String getNewItemsbootStrapServers() {
		return newItemsbootStrapServers;
	}

	public void setNewItemsbootStrapServers(String newItemsbootStrapServers) {
		this.newItemsbootStrapServers = newItemsbootStrapServers;
	}

	public String getNewItemsAcks() {
		return newItemsAcks;
	}

	public void setNewItemsAcks(String newItemsAcks) {
		this.newItemsAcks = newItemsAcks;
	}

	public String getNewItemsRetries() {
		return newItemsRetries;
	}

	public void setNewItemsRetries(String newItemsRetries) {
		this.newItemsRetries = newItemsRetries;
	}

	public String getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}

	public String getValueSerializer() {
		return valueSerializer;
	}

	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public String getNewItemsPublishTopicName() {
		return newItemsPublishTopicName;
	}

	public void setNewItemsPublishTopicName(String newItemsPublishTopicName) {
		this.newItemsPublishTopicName = newItemsPublishTopicName;
	}

	

}
