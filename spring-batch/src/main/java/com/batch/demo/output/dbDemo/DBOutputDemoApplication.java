package com.batch.demo.output.dbDemo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class DBOutputDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBOutputDemoApplication.class, args);
	}
}
