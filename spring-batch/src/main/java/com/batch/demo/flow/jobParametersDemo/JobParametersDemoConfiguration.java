package com.batch.demo.flow.jobParametersDemo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class JobParametersDemoConfiguration implements StepExecutionListener{
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> params;

    @Bean
    public Job myJobParametersDemoJob(){
        return jobBuilderFactory.get("myJobParametersDemoJob")
                .start(myJobParametersDemoStep())
                .build();
    }

    @Bean
    public Step myJobParametersDemoStep() {
        return stepBuilderFactory.get("myJobParametersDemoStep")
                .listener(this)
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("Parameter is : " + params.get("info"));
                    return RepeatStatus.FINISHED;
                })).build();

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        params =  stepExecution.getJobParameters().getParameters();

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
