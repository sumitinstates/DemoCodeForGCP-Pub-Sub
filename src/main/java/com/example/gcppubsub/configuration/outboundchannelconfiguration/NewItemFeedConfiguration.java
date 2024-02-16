package com.example.gcppubsub.configuration.outboundchannelconfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;

@Configuration
public class NewItemFeedConfiguration {
	
	@Bean
	@ServiceActivator(inputChannel = "myOutputChannelForNewItem")
	public MessageHandler messageSenderForNewItem(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "newItem");
	}

	@MessagingGateway(defaultRequestChannel = "myOutputChannelForNewItem")
	public interface PubsubOutboundGateway {
		void sendToPubsub(String text);
	}

}
