package com.batch.demo.output.flatFile;

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
public class FlatFileDemoJobConfiguration {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileDemoJdbcPagingReader")
    private ItemReader<Customer> flatFileDemoJdbcPagingReader;

    @Autowired
    @Qualifier("flatFileDemoFlatFileWriter")
    private ItemWriter<Customer> flatFileDemoFlatFileWriter;

    @Bean
    public Step flatFileOutputDemoStep() {
        return stepBuilderFactory.get("flatFileOutputDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(flatFileDemoJdbcPagingReader)
                .writer(flatFileDemoFlatFileWriter)
                .build();
    }

    @Bean
    public Job flatFileOutputDemoJob() {
        return jobBuilderFactory.get("flatFileOutputDemoJob")
                .start(flatFileOutputDemoStep())
                .build();
    }
}
