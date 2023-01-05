package com.batch.demo.output.flatFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.transform.LineAggregator;

public class MyCustomerLineAggregator implements LineAggregator<Customer> {
    //JSON
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String aggregate(Customer customer) {

        try {
            return mapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
           throw new RuntimeException("Unable to serialize.",e);
        }
    }
}
