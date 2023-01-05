package com.batch.demo.flow.nestedJob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChildJobOneConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step childJob1Step1(){
        return stepBuilderFactory.get("childJob1Step1")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("childJob1Step1");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Job childJob1(){
        return jobBuilderFactory.get("childJob1")
                .start(childJob1Step1())
                .build();
    }
}
