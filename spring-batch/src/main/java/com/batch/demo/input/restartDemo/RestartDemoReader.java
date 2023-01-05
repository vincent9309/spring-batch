package com.batch.demo.input.restartDemo;

import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component("restartDemoReader")
public class RestartDemoReader implements ItemStreamReader<Customer> {


    private Long curLine = 0L;
    private boolean restart = false;

    private FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();

    private ExecutionContext executionContext;

    public RestartDemoReader() {

        reader.setResource(new ClassPathResource("restartDemo.csv"));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"id", "firstName", "lastName", "birthdate"});

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper((fieldSet -> {
            return Customer.builder().id(fieldSet.readLong("id"))
                    .firstName(fieldSet.readString("firstName"))
                    .lastName(fieldSet.readString("lastName"))
                    .birthdate(fieldSet.readString("birthdate"))
                    .build();
        }));
        lineMapper.afterPropertiesSet();

        reader.setLineMapper(lineMapper);
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException {

        Customer customer = null;

        this.curLine++;

        if (restart) {
            reader.setLinesToSkip(this.curLine.intValue()-1);
            restart = false;
            System.out.println("Start reading from line: " + this.curLine);
        }

        reader.open(this.executionContext);

        customer = reader.read();

        if (customer != null) {
            if (customer.getFirstName().equals("wrongName"))
                throw new RuntimeException("Something wrong. Customer id: " + customer.getId());
        } else {
            curLine--;
        }
        return customer;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
        if (executionContext.containsKey("curLine")) {
            this.curLine = executionContext.getLong("curLine");
            this.restart = true;
        } else {
            this.curLine = 0L;
            executionContext.put("curLine", this.curLine.intValue());
        }

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("update curLine: " + this.curLine);
        executionContext.put("curLine", this.curLine);

    }

    @Override
    public void close() throws ItemStreamException {

    }
}
