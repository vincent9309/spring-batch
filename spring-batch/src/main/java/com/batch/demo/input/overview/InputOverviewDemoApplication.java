package com.batch.demo.input.overview;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class InputOverviewDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InputOverviewDemoApplication.class, args);
	}
}
