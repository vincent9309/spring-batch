package com.batch.demo.output.xmlFile;

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
public class XMLFileDemoJobConfiguration {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("xmlFileDemoJdbcPagingReader")
    private ItemReader<Customer> xmlFileDemoJdbcPagingReader;

    @Autowired
    @Qualifier("xmlFileDemoXMLFileWriter")
    private ItemWriter<Customer> xmlFileDemoXMLFileWriter;

    @Bean
    public Step xmlFileOutputDemoStep() {
        return stepBuilderFactory.get("xmlFileOutputDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(xmlFileDemoJdbcPagingReader)
                .writer(xmlFileDemoXMLFileWriter)
                .build();
    }

    @Bean
    public Job xmlFileOutputDemoJob() {
        return jobBuilderFactory.get("xmlFileOutputDemoJob")
                .start(xmlFileOutputDemoStep())
                .build();
    }
}
