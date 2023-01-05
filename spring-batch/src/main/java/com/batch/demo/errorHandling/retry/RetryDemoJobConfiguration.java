package com.batch.demo.errorHandling.retry;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RetryDemoJobConfiguration {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemWriter<String> retryItemWriter;

    @Autowired
    private ItemProcessor<String, String> retryItemProcessor;

    @Bean
    @StepScope
    public ListItemReader reader() {

        List<String> items = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            items.add(String.valueOf(i));
        }

        ListItemReader<String> reader = new ListItemReader(items);

        return reader;
    }





    @Bean
    public Step retryDemoStep() {
        return stepBuilderFactory.get("retryDemoStep")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(retryItemProcessor)
                .writer(retryItemWriter)
                .faultTolerant()
                .retry(CustomRetryableException.class)
                .retryLimit(10)
                .build();
    }

    @Bean
    public Job retryDemoJob() {
        return jobBuilderFactory.get("retryDemoJob")
                .start(retryDemoStep())
                .build();
    }
}
