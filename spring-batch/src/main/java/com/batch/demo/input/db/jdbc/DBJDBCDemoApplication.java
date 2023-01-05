package com.batch.demo.input.db.jdbc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class DBJDBCDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DBJDBCDemoApplication.class, args);
	}
}
