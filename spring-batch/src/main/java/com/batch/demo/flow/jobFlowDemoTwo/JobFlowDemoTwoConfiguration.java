package com.batch.demo.flow.jobFlowDemoTwo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobFlowDemoTwoConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step jobFlowDemoTwoStep1(){
        return stepBuilderFactory.get("jobFlowDemoTwoStep1")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("jobFlowDemoTwoStep1");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step jobFlowDemoTwoStep2(){
        return stepBuilderFactory.get("jobFlowDemoTwoStep2")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("jobFlowDemoTwoStep2");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step jobFlowDemoTwoStep3(){
        return stepBuilderFactory.get("jobFlowDemoTwoStep3")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("jobFlowDemoTwoStep3");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Flow jobFlowDemoFlow1(){
        return new FlowBuilder<Flow>("jobFlowDemoFlow1")
                .start(jobFlowDemoTwoStep1())
                .next(jobFlowDemoTwoStep2())
                .build();
    }

    @Bean
    public Job jobFlowDemoTwoJob(){
        return jobBuilderFactory.get("jobFlowDemoTwoJob")
                .start(jobFlowDemoFlow1())
                .next(jobFlowDemoTwoStep3()).end()
                .build();
    }
}
