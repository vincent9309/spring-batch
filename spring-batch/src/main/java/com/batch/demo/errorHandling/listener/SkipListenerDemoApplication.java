package com.batch.demo.errorHandling.listener;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SkipListenerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkipListenerDemoApplication.class, args);
	}
}
