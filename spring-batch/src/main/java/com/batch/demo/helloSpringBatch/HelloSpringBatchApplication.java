package com.batch.demo.helloSpringBatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringBatchApplication.class, args);
	}
}
