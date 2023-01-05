package com.batch.demo.input.overview;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
@Configuration
public class InputOverviewDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job InputOverviewDemoJob(){
        return jobBuilderFactory.get("InputOverviewDemoJob")
                .start(inputOverviewDemoStep())
                .build();

    }

    public Step inputOverviewDemoStep() {
        return stepBuilderFactory.get("inputOverviewDemoStep")
                .<String,String>chunk(2)
                .reader(inputOverviewDemoReader())
                .writer(list->{
                    for (String item:list){
                        System.out.println("current item is: " + item);
                    }
                }).build();
    }

    @Bean
    public InputOverviewDemoItemReader inputOverviewDemoReader() {
        List<String> data = Arrays.asList("one","two","three");
        return new InputOverviewDemoItemReader(data);

    }
}
