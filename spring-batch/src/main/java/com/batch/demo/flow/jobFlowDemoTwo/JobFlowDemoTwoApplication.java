package com.batch.demo.flow.jobFlowDemoTwo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class JobFlowDemoTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobFlowDemoTwoApplication.class, args);
	}
}
