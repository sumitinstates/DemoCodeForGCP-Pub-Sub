package com.example.gcppubsub.outboundchannel;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "myOutputChannelForNewItem")
public interface NewItemFeedChannel {

	void publish(String message); 
}
