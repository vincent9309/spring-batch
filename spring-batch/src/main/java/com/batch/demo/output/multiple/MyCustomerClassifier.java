package com.batch.demo.output.multiple;

import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;


public class MyCustomerClassifier implements Classifier<Customer, ItemWriter<? super Customer>> {

    private ItemWriter<Customer> xmlWriter;
    private ItemWriter<Customer> jsonWriter;

    public MyCustomerClassifier(ItemWriter<Customer> xmlWriter, ItemWriter<Customer>
            jsonWriter) {
        this.xmlWriter = xmlWriter;
        this.jsonWriter = jsonWriter;

    }

    @Override
    public ItemWriter<? super Customer> classify(Customer item) {

        return item.getId()%2 == 0 ? xmlWriter:jsonWriter;
    }
}
