package com.batch.demo.errorHandling.overview;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ErrorHandlingOverviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErrorHandlingOverviewApplication.class, args);
    }
}
