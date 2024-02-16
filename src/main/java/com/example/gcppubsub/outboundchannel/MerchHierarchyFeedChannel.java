package com.example.gcppubsub.outboundchannel;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "myOutputChannelForMerchHierarchyUpdates")
public interface MerchHierarchyFeedChannel {

	void publish(String message); 
}
