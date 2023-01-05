package com.batch.demo.input.multiple;

import com.batch.demo.input.flatFile.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class MultipleFileDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("flatFileDemoWriter")
    private ItemWriter<? super Customer> flatFileDemoWriter;

    @Value("classpath*:/file*.csv")
    private Resource[] inputFiles;

    @Bean
    public Job multipleFileDemoJob(){
        return jobBuilderFactory.get("multipleFileDemoJob")
                .start(multipleFileDemoStep())
                .build();

    }

    @Bean
    public Step multipleFileDemoStep() {
        return stepBuilderFactory.get("multipleFileDemoStep")
                .<Customer,Customer>chunk(50)
                .reader(multipleResourceItemReader())
                .writer(flatFileDemoWriter)
                .build();
    }

    private MultiResourceItemReader<Customer> multipleResourceItemReader() {

        MultiResourceItemReader<Customer> reader = new MultiResourceItemReader<>();

        reader.setDelegate(flatFileReader());
        reader.setResources(inputFiles);

        return reader;
    }

    @Bean
    public FlatFileItemReader<Customer> flatFileReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("customer.csv"));
       // reader.setLinesToSkip(1);

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
