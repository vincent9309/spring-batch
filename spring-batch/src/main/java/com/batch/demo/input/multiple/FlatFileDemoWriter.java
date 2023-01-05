package com.batch.demo.input.multiple;

import com.batch.demo.input.flatFile.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("flatFileDemoWriter")
public class FlatFileDemoWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        for (Customer customer:items)
            System.out.println(customer);

    }
}
