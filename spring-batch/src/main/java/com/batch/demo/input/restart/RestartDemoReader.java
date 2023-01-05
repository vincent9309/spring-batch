package com.batch.demo.input.restart;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("restartDemoReader")
public class RestartDemoReader implements ItemStreamReader<Customer> {
    @Autowired
    @Qualifier("customerFlatFileItemReader")
    private FlatFileItemReader<Customer> customerFlatFileItemReader;

    private Long curLine = 0L;
    private boolean restart = false;

    private ExecutionContext executionContext;

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException {

        Customer customer = null;

        this.curLine++;

       if(restart){
            customerFlatFileItemReader.setLinesToSkip(this.curLine.intValue()-1);
            restart = false;
            System.out.println("Start reading from line: " + this.curLine);
        }

        customerFlatFileItemReader.open(this.executionContext);
        customer = customerFlatFileItemReader.read();


        if (customer!=null && customer.getFirstName().equals("wrongName")) {
            throw new RuntimeException("Something wrong. Customer id: " + customer.getId());
        }
        return customer;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
        if (executionContext.containsKey("curLine")){
            this.curLine = executionContext.getLong("curLine");
            this.restart = true;
        }else{
            this.curLine = 0L;
            executionContext.put("curLine",this.curLine);
            System.out.println("Start reading from line: " + this.curLine + 1);
        }

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("curLine",this.curLine);

    }

    @Override
    public void close() throws ItemStreamException {

    }
}
