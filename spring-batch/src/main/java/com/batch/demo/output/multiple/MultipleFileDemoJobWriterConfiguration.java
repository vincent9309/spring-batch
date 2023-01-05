package com.batch.demo.output.multiple;


import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultipleFileDemoJobWriterConfiguration {

    @Bean
    public StaxEventItemWriter<Customer> xmlFileWriter() throws Exception {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String,Class> aliases = new HashMap<>();
        aliases.put("customer",Customer.class);
        marshaller.setAliases(aliases);

        StaxEventItemWriter<Customer> itemWriter = new StaxEventItemWriter<>();

        itemWriter.setRootTagName("customers");
        itemWriter.setMarshaller(marshaller);

        String path = File.createTempFile("multiInfo",".xml").getAbsolutePath();
        System.out.println(">> xml file is created in: " + path);
        itemWriter.setResource(new FileSystemResource(path));
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public FlatFileItemWriter<Customer> jsonFileWriter() throws Exception {
        FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();
        String path = File.createTempFile("multiInfo",".json").getAbsolutePath();
        System.out.println(">> json file is created in: " + path);
        itemWriter.setResource(new FileSystemResource(path));

        itemWriter.setLineAggregator(new MyCustomerLineAggregator());
        itemWriter.afterPropertiesSet();

        return itemWriter;

    }



//    @Bean
//    public CompositeItemWriter<Customer> customerCompositeItemWriter() throws Exception {
//        CompositeItemWriter<Customer> itemWriter = new CompositeItemWriter<>();
//        itemWriter.setDelegates(Arrays.asList(xmlFileWriter(),jsonFileWriter()));
//        itemWriter.afterPropertiesSet();
//        return itemWriter;
//    }

    @Bean
    public ClassifierCompositeItemWriter<Customer> customerCompositeItemWriter() throws Exception {
        ClassifierCompositeItemWriter<Customer> itemWriter = new ClassifierCompositeItemWriter<>();


        itemWriter.setClassifier(new MyCustomerClassifier(xmlFileWriter(),jsonFileWriter()));

        return itemWriter;
    }






}
