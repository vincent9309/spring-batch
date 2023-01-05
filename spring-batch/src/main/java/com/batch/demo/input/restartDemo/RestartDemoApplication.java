package com.batch.demo.input.restartDemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class RestartDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestartDemoApplication.class, args);
	}
}
