package com.batch.demo.schedule;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class JobLaunchDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobLaunchDemoApplication.class, args);
    }
}
