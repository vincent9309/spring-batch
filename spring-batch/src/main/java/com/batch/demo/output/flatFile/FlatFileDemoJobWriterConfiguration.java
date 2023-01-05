package com.batch.demo.output.flatFile;


import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class FlatFileDemoJobWriterConfiguration {

    @Bean
    public FlatFileItemWriter<Customer> flatFileDemoFlatFileWriter() throws Exception {
        FlatFileItemWriter<Customer> itemWriter = new FlatFileItemWriter<>();
        String path = File.createTempFile("customerInfo",".data").getAbsolutePath();
        System.out.println(">> file is created in: " + path);
        itemWriter.setResource(new FileSystemResource(path));

        itemWriter.setLineAggregator(new MyCustomerLineAggregator());
        itemWriter.afterPropertiesSet();

        return itemWriter;

    }


}
