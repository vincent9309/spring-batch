package com.batch.demo.output.multiple;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultipleFileDemoJobReaderConfiguration {

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Customer> multipleFileDemoJdbcPagingReader() {
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(this.dataSource);
        reader.setFetchSize(10);
        reader.setRowMapper((rs,rowNum)->{
            return Customer.builder().id(rs.getLong("id"))
                    .firstName(rs.getString("firstName"))
                    .lastName(rs.getString("lastName"))
                    .birthdate(rs.getString("birthdate"))
                    .build();

        });

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
        queryProvider.setFromClause("from customer");

        Map<String, Order> sortKeys = new HashMap<>(1);

        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        reader.setQueryProvider(queryProvider);

        return reader;
    }
}
