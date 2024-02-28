package com.example.gcppubsub.kafka.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class FeedPublisherToKafka {
	
	private static final Logger LOG = LoggerFactory.getLogger(FeedPublisherToKafka.class);

	@Autowired
	private KafkaNewItemConfig kafkaNewItemConfig;
	
	@Autowired
	PublisherService publisherService;
	
	/*@Autowired
	private KafkaBrickIdUpdatesConfig kafkaBrickIdUpdatesConfig;
	
	@Autowired
	private KafkaMerchHierarchyUpdatesConfig kafkaMerchHierarchyUpdatesConfig;*/

	private KafkaProducer<String, String> kafkaNewItemProducer = null;
	private KafkaProducer<String, String> kafkaBrickIdUpdatesProducer = null;
	private KafkaProducer<String, String> kafkaMerchHierarchyUpdatesProducer = null;

	private static final String KEYSTORE_PSWD = "keystore.password";
	private static final String TRUSTSTORE_PSWD = "truststore.password";
	private static final String KEY_PSWD = "key.password";

	@PostConstruct
    public void init() throws IOException {	
		kafkaNewItemProducer = this.createProducer(this.kafkaNewItemConfig);
		//publisherService.listener();
		
    }
	
	/*public void publishMessageForMerchHierarchyUpdates(String message) {
		//LOG.info("Item with updated mechandise hierarchy = {}", message);
		if (this.kafkaMerchHierarchyUpdatesConfig == null) return;
		String topicName = kafkaMerchHierarchyUpdatesConfig.getTopicName();
		try {
			ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message);
			kafkaMerchHierarchyUpdatesProducer.send(record);
			LOG.info("New_Item_Message = success");
		} catch (Exception e) {
			LOG.error("New_Item_Message = failed with exception : {} and cause : {}", e.getMessage(), e.getCause());
		}
	}*/
	
	public void publishMessageForNewItems(String message) {
		
		LOG.info("New item is = {}", message);
		if (this.kafkaNewItemConfig == null)
			return;
		String topicName = kafkaNewItemConfig.getNewItemsPublishTopicName();
		try {
			ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message);
			kafkaNewItemProducer.send(record);
			LOG.info("New_Item_Message = success");
		} catch (Exception e) {
			LOG.error("New_Item_Message = failed with exception : {} and cause : {}", e.getMessage(), e.getCause());
		}
	}

	/*public void publishMessageForBrickIdUpdates(String message) {
		LOG.info("New item is = {}", message);
		if (this.kafkaBrickIdUpdatesConfig == null)
			return;
		String topicName = kafkaBrickIdUpdatesConfig.getTopicName();
		try {
			ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message);
			kafkaBrickIdUpdatesProducer.send(record);
			LOG.info("New_Item_Message = success");
		} catch (Exception e) {
			LOG.error("New_Item_Message = failed with exception : {} and cause : {}", e.getMessage(), e.getCause());
		}
	}*/

	private Properties loadProperties(KafkaNewItemConfig kafkaNewItemConfig) throws IOException {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaNewItemConfig.getNewItemsbootStrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaNewItemConfig.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaNewItemConfig.getValueSerializer());
		props.put(ProducerConfig.ACKS_CONFIG, kafkaNewItemConfig.getNewItemsAcks());
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaNewItemConfig.getNewItemsRetries());
		// jiawzhang NOTICE: add for fraud sox kafka
		/*props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,
				this.getClass().getClassLoader().getResource(kafkaNewItemConfig.getKeystore()).getPath());
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
				this.getClass().getResource(kafkaNewItemConfig.getTruststore()).getPath());
		Properties soxKafkaPasswordProperties = new Properties();
		soxKafkaPasswordProperties.load(new FileInputStream(kafkaNewItemConfig.getSoxKafkaPasswordPath()));
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(KEYSTORE_PSWD));
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(TRUSTSTORE_PSWD));
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(KEY_PSWD));
		props.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, SslConfigs.DEFAULT_SSL_KEYSTORE_TYPE);
		props.put(SslConfigs.SSL_PROTOCOL_CONFIG, "SSL");
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.put(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, "TLSv1.2");
		props.put(SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG, "SunX509");*/
		return props;
	}

	/*private Properties loadProperties(KafkaBrickIdUpdatesConfig kafkaBrickIdUpdatesConfig) throws IOException {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrickIdUpdatesConfig.getBootStrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaBrickIdUpdatesConfig.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaBrickIdUpdatesConfig.getValueSerializer());
		props.put(ProducerConfig.ACKS_CONFIG, kafkaBrickIdUpdatesConfig.getAcks());
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaBrickIdUpdatesConfig.getRetries());
		// jiawzhang NOTICE: add for fraud sox kafka
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,
				this.getClass().getClassLoader().getResource(kafkaNewItemConfig.getKeystore()).getPath());
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
				this.getClass().getResource(kafkaNewItemConfig.getTruststore()).getPath());
		Properties soxKafkaPasswordProperties = new Properties();
		soxKafkaPasswordProperties.load(new FileInputStream(kafkaBrickIdUpdatesConfig.getSoxKafkaPasswordPath()));
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(KEYSTORE_PSWD));
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(TRUSTSTORE_PSWD));
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(KEY_PSWD));
		props.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, SslConfigs.DEFAULT_SSL_KEYSTORE_TYPE);
		props.put(SslConfigs.SSL_PROTOCOL_CONFIG, "SSL");
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.put(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, "TLSv1.2");
		props.put(SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG, "SunX509");
		return props;
	}

	private Properties loadProperties(KafkaMerchHierarchyUpdatesConfig kafkaMerchHierarchyUpdatesConfig)
			throws IOException {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaMerchHierarchyUpdatesConfig.getBootStrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaMerchHierarchyUpdatesConfig.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaMerchHierarchyUpdatesConfig.getValueSerializer());
		props.put(ProducerConfig.ACKS_CONFIG, kafkaMerchHierarchyUpdatesConfig.getAcks());
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaMerchHierarchyUpdatesConfig.getRetries());
		// jiawzhang NOTICE: add for fraud sox kafka
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG,
				this.getClass().getClassLoader().getResource(kafkaMerchHierarchyUpdatesConfig.getKeystore()).getPath());
		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
				this.getClass().getResource(kafkaMerchHierarchyUpdatesConfig.getTruststore()).getPath());
		Properties soxKafkaPasswordProperties = new Properties();
		soxKafkaPasswordProperties
				.load(new FileInputStream(kafkaMerchHierarchyUpdatesConfig.getSoxKafkaPasswordPath()));
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(KEYSTORE_PSWD));
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(TRUSTSTORE_PSWD));
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, soxKafkaPasswordProperties.getProperty(KEY_PSWD));
		props.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, SslConfigs.DEFAULT_SSL_KEYSTORE_TYPE);
		props.put(SslConfigs.SSL_PROTOCOL_CONFIG, "SSL");
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		props.put(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, "TLSv1.2");
		props.put(SslConfigs.SSL_KEYMANAGER_ALGORITHM_CONFIG, "SunX509");
		return props;
	}*/

	private KafkaProducer<String, String> createProducer(KafkaNewItemConfig kafkaNewItemConfig) throws IOException {
		if (kafkaNewItemConfig == null)
			return null;
		Properties props = this.loadProperties(kafkaNewItemConfig);
		return new KafkaProducer<>(props);
	}

	/*private KafkaProducer<String, String> createProducer(KafkaBrickIdUpdatesConfig kafkaBrickIdUpdatesConfig)
			throws IOException {
		if (kafkaBrickIdUpdatesConfig == null)
			return null;
		Properties props = this.loadProperties(kafkaBrickIdUpdatesConfig);
		return new KafkaProducer<>(props);
	}

	private KafkaProducer<String, String> createProducer(
			KafkaMerchHierarchyUpdatesConfig kafkaMerchHierarchyUpdatesConfig) throws IOException {
		if (kafkaMerchHierarchyUpdatesConfig == null)
			return null;
		Properties props = this.loadProperties(kafkaMerchHierarchyUpdatesConfig);
		return new KafkaProducer<>(props);
	}*/


}

