package com.batch.demo.input.restartDemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestartDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileDemoWriter")
    private ItemWriter<? super Customer> flatFileDemoWriter;

    @Autowired
    @Qualifier("restartDemoReader")
    private ItemReader<Customer> restartDemoReader;

    @Bean
    public Job restartDemoJob(){
        return jobBuilderFactory.get("restartDemoJob")
                .start(restartDemoStep())
                .build();

    }

    @Bean
    public Step restartDemoStep() {
        return stepBuilderFactory.get("restartDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(restartDemoReader)
                .writer(flatFileDemoWriter)
                .build();
    }


}
