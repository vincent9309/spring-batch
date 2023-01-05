package com.batch.demo.output.dbDemo;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class DbOutputDemoJobReaderConfiguration {

    @Bean
    public FlatFileItemReader<Customer> dbOutputDemoJobFlatFileReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource("customerInit.csv"));

        DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"id","firstName", "lastName", "birthdate"});

        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper((fieldSet -> {
            return Customer.builder().id(fieldSet.readLong("id"))
                    .firstName(fieldSet.readString("firstName"))
                    .lastName(fieldSet.readString("lastName"))
                    .birthdate(fieldSet.readString("birthdate"))
                    .build();
        }));
        customerLineMapper.afterPropertiesSet();

        reader.setLineMapper(customerLineMapper);

        return reader;
    }
}
