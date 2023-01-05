package com.batch.demo.output.dbDemo;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbOutputDemoJobWriterConfiguration {

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Customer> dbOutputDemoJobFlatFileWriter(){
        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into customer(id,firstName,lastName,birthdate) values " +
                "(:id,:firstName,:lastName,:birthdate)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }


}
