package com.example.gcppubsub.configuration.inboundchannelconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.example.gcppubsub.kafka.configuration.FeedPublisherToKafka;
import com.example.gcppubsub.outboundchannel.BrickItemUpdateFeedChannel;
import com.example.gcppubsub.outboundchannel.NewItemFeedChannel;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;

@Configuration
public class InboundConfiguration {

	// This is where the adapter sends the received messages to

	@Bean
	public MessageChannel pubsubInputChannel() {

		return new DirectChannel();

	}
	
	@Autowired
	//private PubSubConfiguration.PubsubOutboundGateway messagingGateway;
	NewItemFeedChannel messagingGateway;
	
	@Autowired
	BrickItemUpdateFeedChannel brickItemUpdateFeedChannelMessagingGateway;
	
	@Autowired
	FeedPublisherToKafka feedPublisherToKafka;
    


	/*
	 * An inbound channel adapter listens to messages from a Google Cloud Pub/Sub
	 * subscription and sends them to a Spring channel in an application.
	 * 
	 * 
	 * Instantiating an inbound channel adapter requires a PubSubTemplate instance
	 * and the name of an existing subscription.
	 * 
	 * 
	 * PubSubTemplate is Spring’s abstraction to subscribe to Google Cloud Pub/Sub
	 * topics.
	 * 
	 * 
	 * The Spring Cloud GCP Pub/Sub Boot starter provides an auto-configured
	 * PubSubTemplate instance which you can simply inject as a method argument.
	 * 
	 * 
	 */

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
		@Qualifier("pubsubInputChannelForNewItem") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate,
				"projects/pubsubproject-414417/subscriptions/newItem-sub");
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;

	}
	
	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapterForBrickItemUpdates(
		@Qualifier("pubsubInputChannelForBrickItemUpdates") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate,
				"projects/pubsubproject-414417/subscriptions/brickItemUpdates-sub");
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;

	}
	
	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapterForMerchHierarchyUpdates(
		@Qualifier("pubsubInputChannelForMerchHierarchyUpdates") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate,
				"projects/pubsubproject-414417/subscriptions/merchHierarchyUpdates-sub");
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		return adapter;

	}

	// It is used to process incoming messages
	@Bean
	@ServiceActivator(inputChannel = "pubsubInputChannelForNewItem")
	public MessageHandler messageReceiverForNewItem() {

		return message -> {
			System.out.println("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
			
			//messagingGateway.publish(new String((byte[]) message.getPayload())+ "-updated");

			feedPublisherToKafka.publishMessageForNewItems(new String((byte[]) message.getPayload()));
			BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders()
					.get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
			originalMessage.ack();

		};

	}
	
	@Bean
	@ServiceActivator(inputChannel = "pubsubInputChannelForBrickItemUpdates")
	public MessageHandler messageReceiverForBrickItemUpdates() {

		return message -> {
			System.out.println("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
			
			//messagingGateway.publish(new String((byte[]) message.getPayload())+ "-updated");

			BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders()
					.get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
			originalMessage.ack();

		};

	}
	
	@Bean
	@ServiceActivator(inputChannel = "pubsubInputChannelForMerchHierarchyUpdates")
	public MessageHandler messageReceiverForMerchHierarchyUpdates() {

		return message -> {
			System.out.println("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
			
			//messagingGateway.publish(new String((byte[]) message.getPayload())+ "-updated");

			BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders()
					.get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
			originalMessage.ack();

		};

	}

}
