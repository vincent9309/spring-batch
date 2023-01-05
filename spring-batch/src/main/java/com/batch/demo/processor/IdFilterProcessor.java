package com.batch.demo.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class IdFilterProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        if (item.getId() % 2 == 0){
            return item;
        } else {
            return null;
        }
    }
}
