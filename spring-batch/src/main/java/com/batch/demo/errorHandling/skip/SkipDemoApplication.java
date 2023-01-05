package com.batch.demo.errorHandling.skip;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SkipDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkipDemoApplication.class, args);
	}
}
