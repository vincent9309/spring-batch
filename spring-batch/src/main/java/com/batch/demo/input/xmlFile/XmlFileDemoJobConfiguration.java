package com.batch.demo.input.xmlFile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class XmlFileDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("xmlFileDemoWriter")
    private ItemWriter<? super Customer> xmlFileDemoWriter;

    @Bean
    public Job xmlFileDemoJob(){
        return jobBuilderFactory.get("xmlFileDemoJob")
                .start(xmlFileDemoStep())
                .build();

    }

    @Bean
    public Step xmlFileDemoStep() {
        return stepBuilderFactory.get("xmlFileDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(xmlFileDemoReader())
                .writer(xmlFileDemoWriter)
                .build();
    }

    @Bean
    @StepScope
    public StaxEventItemReader<Customer> xmlFileDemoReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();

        reader.setResource(new ClassPathResource("customer.xml"));
        reader.setFragmentRootElementName("customer");


        XStreamMarshaller unMarshaller = new XStreamMarshaller();
        Map<String,Class> map = new HashMap<>();
        map.put("customer",Customer.class);
        unMarshaller.setAliases(map);
        reader.setUnmarshaller(unMarshaller);


        return reader;

    }
}
