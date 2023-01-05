package com.batch.demo.input.multiple;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class MultipleFileDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleFileDemoApplication.class, args);
	}
}
