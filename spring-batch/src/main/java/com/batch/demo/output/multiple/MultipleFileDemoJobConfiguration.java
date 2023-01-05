package com.batch.demo.output.multiple;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipleFileDemoJobConfiguration {

    @Autowired
    @Qualifier("xmlFileWriter")
    private ItemStreamWriter<Customer> xmlFileWriter;

    @Autowired
    @Qualifier("jsonFileWriter")
    private ItemStreamWriter<Customer> jsonFileWriter;


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("multipleFileDemoJdbcPagingReader")
    private ItemReader<Customer> multipleFileDemoJdbcPagingReader;

    @Autowired
    @Qualifier("customerCompositeItemWriter")
    private ItemWriter<Customer> customerCompositeItemWriter;



    @Bean
    public Step multipleFileOutputDemoStep22() {
        return stepBuilderFactory.get("multipleFileOutputDemoStep22")
                .<Customer,Customer>chunk(10)
                .reader(multipleFileDemoJdbcPagingReader)
                .writer(customerCompositeItemWriter)
                .stream(xmlFileWriter)
                .stream(jsonFileWriter)
                .build();
    }

    @Bean
    public Job multipleFileOutputDemoJob22() {
        return jobBuilderFactory.get("multipleFileOutputDemoJob22")
                .start(multipleFileOutputDemoStep22())
                .build();
    }
}
