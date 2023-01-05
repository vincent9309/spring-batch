package com.batch.demo.output.overview;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("outputOverviewWriter")
public class OutputOverviewWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        System.out.println("Chunk size is: " + items.size());
        for (String item:items){
            System.out.println(item);
        }

    }
}
