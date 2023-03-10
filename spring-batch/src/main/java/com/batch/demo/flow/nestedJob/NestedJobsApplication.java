package com.batch.demo.flow.nestedJob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class NestedJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NestedJobsApplication.class, args);
    }
}

