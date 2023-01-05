package com.batch.demo.input.restart;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class RestartDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileDemoWriter")
    private ItemWriter<? super Customer> flatFileDemoWriter;

    @Autowired
    @Qualifier("restartDemoReader")
    private RestartDemoReader restartDemoReader;

    @Bean
    public Job restartDemoJob10(){
        return jobBuilderFactory.get("restartDemoJob10")
                .start(restartDemoStep10())
                .build();

    }

    @Bean
    public Step restartDemoStep10() {
        return stepBuilderFactory.get("restartDemoStep10")
                .<Customer,Customer>chunk(10)
                .reader(restartDemoReader)
                .writer(flatFileDemoWriter)
                .build();
    }

    @Bean("customerFlatFileItemReader")
    public FlatFileItemReader<Customer> flatFileDemoReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("restartDemo.csv"));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"id","firstName","lastName","birthdate"});

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper((fieldSet -> {
            return Customer.builder().id(fieldSet.readLong("id"))
                    .firstName(fieldSet.readString("firstName"))
                    .lastName(fieldSet.readString("lastName"))
                    .birthdate(fieldSet.readString("birthdate"))
                    .build();
        }));
        lineMapper.afterPropertiesSet();

        reader.setLineMapper(lineMapper);

        return reader;

    }
}
