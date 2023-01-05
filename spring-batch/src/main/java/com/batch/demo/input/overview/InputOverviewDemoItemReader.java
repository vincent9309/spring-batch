package com.batch.demo.input.overview;

import org.springframework.batch.item.*;

import java.util.Iterator;
import java.util.List;

public class InputOverviewDemoItemReader implements ItemReader<String> {
    private final Iterator<String> iterator;

    public InputOverviewDemoItemReader(List<String> data) {
        this.iterator = data.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
       if(this.iterator.hasNext()){
           return this.iterator.next();
       }else {
           return null;
       }
    }
}
