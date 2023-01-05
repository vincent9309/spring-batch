package com.batch.demo.errorHandling.listener;

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
public class SkipListenerDemoJobConfiguration {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemWriter<String> skipItemWriter;

    @Autowired
    private ItemProcessor<String, String> skipItemProcessor;

    @Autowired
    private MySkipListener mySkipListener;

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
    public Step skipListenerDemoStep1() {
        return stepBuilderFactory.get("skipListenerDemoStep1")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(skipItemProcessor)
                .writer(skipItemWriter)
                .faultTolerant()
                .skip(CustomRetryableException.class)
                .skipLimit(10)
                .listener(mySkipListener)
                .build();
    }

    @Bean
    public Job skipListenerDemoJob1() {
        return jobBuilderFactory.get("skipListenerDemoJob1")
                .start(skipListenerDemoStep1())
                .build();
    }
}
