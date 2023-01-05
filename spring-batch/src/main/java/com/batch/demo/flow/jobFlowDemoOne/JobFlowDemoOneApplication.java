package com.batch.demo.flow.jobFlowDemoOne;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class JobFlowDemoOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobFlowDemoOneApplication.class, args);
	}
}
