package com.batch.demo.input.db.jdbc;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DBJdbcDemoJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbJdbcDemoWriter")
    private ItemWriter<? super Customer> dbJdbcDemoWriter;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job DBJdbcDemoJob(){
        return jobBuilderFactory.get("DBJdbcDemoJob")
                .start(dbJdbcDemoStep())
                .build();

    }

    @Bean
    public Step dbJdbcDemoStep() {
        return stepBuilderFactory.get("dbJdbcDemoStep")
                .<Customer,Customer>chunk(100)
                .reader(dbJdbcDemoReader())
                .writer(dbJdbcDemoWriter)
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
}
