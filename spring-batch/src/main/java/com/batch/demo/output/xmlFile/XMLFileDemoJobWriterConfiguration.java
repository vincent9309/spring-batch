package com.batch.demo.output.xmlFile;


import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class XMLFileDemoJobWriterConfiguration {

    @Bean
    public StaxEventItemWriter<Customer> xmlFileDemoXMLFileWriter() throws Exception {
        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String,Class> aliases = new HashMap<>();
        aliases.put("customer",Customer.class);
        marshaller.setAliases(aliases);

        StaxEventItemWriter<Customer> itemWriter = new StaxEventItemWriter<>();

        itemWriter.setRootTagName("customers");
        itemWriter.setMarshaller(marshaller);

        String path = File.createTempFile("customerInfo",".xml").getAbsolutePath();
        System.out.println(">> xml file is generated: " + path);
        itemWriter.setResource(new FileSystemResource(path));
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }



}
