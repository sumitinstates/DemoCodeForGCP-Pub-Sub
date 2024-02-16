package com.example.gcppubsub;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gcppubsub.outboundchannel.NewItemFeedChannel;

import jakarta.annotation.PostConstruct;

@Service
public class PubSubService {
	
	@Autowired
	NewItemFeedChannel messagingGateway;

	/*@PostConstruct
    public void init() throws IOException {	
		
		System.out.println("Going to send Message....");
		//messagingGateway.sendToPubsub("Hi ! Message published");
		messagingGateway.publish("Arpita you are pretty");
		System.out.println("Message Published !");
		//kafkaBrickIdUpdatesProducer = this.createProducer(this.kafkaBrickIdUpdatesConfig);
		//kafkaMerchHierarchyUpdatesProducer = this.createProducer(this.kafkaMerchHierarchyUpdatesConfig);
		
    }*/
}
