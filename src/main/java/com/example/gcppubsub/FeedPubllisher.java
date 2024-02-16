package com.example.gcppubsub;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gcppubsub.outboundchannel.BrickItemUpdateFeedChannel;
import com.example.gcppubsub.outboundchannel.MerchHierarchyFeedChannel;
import com.example.gcppubsub.outboundchannel.NewItemFeedChannel;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/send")
public class FeedPubllisher {
	
	
	//private PubSubConfiguration.PubsubOutboundGateway messagingGateway;
	
	@Autowired
	NewItemFeedChannel messagingGateway;
	
	@Autowired
	MerchHierarchyFeedChannel merchHierarchyFeedChannelGateway;
	
	@Autowired
	BrickItemUpdateFeedChannel brickItemUpdateFeedChannelGateway;

	/*@PostConstruct
    public void init() throws IOException {	
		
		System.out.println("Going to send Message....");
		//messagingGateway.sendToPubsub("Hi ! Message published");
		messagingGateway.publish(message);
		System.out.println("Message Published !");
		//kafkaBrickIdUpdatesProducer = this.createProducer(this.kafkaBrickIdUpdatesConfig);
		//kafkaMerchHierarchyUpdatesProducer = this.createProducer(this.kafkaMerchHierarchyUpdatesConfig);
		
    }*/
	
	@PostMapping("/publish/newItem")
	public String publishNewItem(@RequestBody String message) {
		System.out.println("publishg message ....");
		//messagingGateway.sendToPubsub(message);
		messagingGateway.publish(message);
		
		return "message publishes successfully";
		
	}
	
	@PostMapping("/publish/brickItemUpdates")
	public String publishBrickItemUpdates(@RequestBody String message) {
		System.out.println("publishg message ....");
		brickItemUpdateFeedChannelGateway.publish(message);
		
		return "message publishes successfully";
		
	}
	
	@PostMapping("/publish/merchHierarchyUpdates")
	public String publishMerchHierarchyUpdates(@RequestBody String message) {
		System.out.println("publishg message ....");
		merchHierarchyFeedChannelGateway.publish(message);
		
		return "message publishes successfully";
		
	}
}
