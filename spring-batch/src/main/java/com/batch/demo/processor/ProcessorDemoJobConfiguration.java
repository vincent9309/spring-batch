package com.batch.demo.processor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ProcessorDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ItemProcessor<Customer, Customer> firstNameUpperCaseProcessor;

    @Autowired
    private ItemProcessor<Customer, Customer> idFilterProcessor;

    @Bean
    public Job processorDemoJob() throws Exception {
        return jobBuilderFactory.get("processorDemoJob")
                .start(processorDemoStep())
                .build();

    }
    
    @Bean
    public CompositeItemProcessor<Customer,Customer> processorDemoProcessor(){
        CompositeItemProcessor<Customer,Customer> processor = new CompositeItemProcessor<>();

        List<ItemProcessor<Customer,Customer>> list = new ArrayList<>();
        list.add(firstNameUpperCaseProcessor);
        list.add(idFilterProcessor);
        processor.setDelegates(list);
        
        return processor;
    }

    @Bean
    public Step processorDemoStep() throws Exception {
        return stepBuilderFactory.get("processorDemoStep")
                .<Customer,Customer>chunk(100)
                .reader(dbJdbcDemoReader())
                .processor(processorDemoProcessor())
                .writer(flatFileDemoFlatFileWriter())
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Customer> dbJdbcDemoReader() {
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(this.dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper((rs,rowNum)->{
            return Customer.builder().id(rs.getLong("id"))
                    .firstName(rs.getString("firstName"))
                    .lastName(rs.getString("lastName"))
                    .birthdate(rs.getString("birthdate"))
                    .build();

        });

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
        queryProvider.setFromClause("from Customer");
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        reader.setQueryProvider(queryProvider);

        return reader;

    }

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
