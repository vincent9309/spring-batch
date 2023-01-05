package com.batch.demo.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstNameUpperCaseProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        return new Customer(item.getId(),item.getFirstName().toUpperCase(),item.getLastName(),
                item.getBirthdate());
    }
}
