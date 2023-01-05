package com.batch.demo.flow.flowDecisionDemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class FlowDecisionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowDecisionDemoApplication.class, args);
	}
}
