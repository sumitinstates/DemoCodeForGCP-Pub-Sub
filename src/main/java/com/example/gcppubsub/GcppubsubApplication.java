package com.example.gcppubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class GcppubsubApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GcppubsubApplication.class, args);
		
	}
}
