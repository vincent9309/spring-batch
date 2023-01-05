package com.batch.demo.input.db.jdbc;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("dbJdbcDemoWriter")
public class DbJdbcDemoWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        for (Customer customer:items)
            System.out.println(customer);

    }
}
