package com.batch.demo.schedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class MyJobIncrementor implements JobParametersIncrementer {
    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters jobParameters =  (parameters==null) ? new JobParameters() : parameters;
        return new JobParametersBuilder(jobParameters).addDate("jobScheduledparam",new Date()).toJobParameters();
    }
}
