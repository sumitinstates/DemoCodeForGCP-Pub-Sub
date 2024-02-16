package com.example.gcppubsub.outboundchannel;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "myOutputChannelForBrickItemUpdates")
public interface BrickItemUpdateFeedChannel {
	
	void publish(String message); 

}
