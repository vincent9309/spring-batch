package com.batch.demo.input.xmlFile;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class XMLFileDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XMLFileDemoApplication.class, args);
	}
}
