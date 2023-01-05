package com.batch.demo.input.xmlFile;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("xmlFileDemoWriter")
public class xmlFileDemoWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        for (Customer customer:items)
            System.out.println(customer);

    }
}
