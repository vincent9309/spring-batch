package com.batch.demo.output.overview;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OutputOverviewJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("outputOverviewWriter")
    private ItemWriter<? super String> outputOverviewWriter;

    @Bean
    public Job outputOverviewJob(){
        return jobBuilderFactory.get("outputOverviewJob")
                .start(outputOverviewStep())
                .build();

    }

    @Bean
    public Step outputOverviewStep() {
        return stepBuilderFactory.get("outputOverviewStep")
                .<String,String>chunk(10)
                .reader(outputOverviewReader())
                .writer(outputOverviewWriter)
                .build();
    }

    @Bean
    public ListItemReader<String> outputOverviewReader() {
        List<String> items = new ArrayList<>();
        for(int i=0;i<100;i++){
            items.add("item "+i);

        }
        return new ListItemReader<>(items);

    }
}
