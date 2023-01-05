package com.batch.demo.flow.flowDecisionDemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowDecisionDemoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("firstStep");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step oddStep(){
        return stepBuilderFactory.get("oddStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("oddStep");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step evenStep(){
        return stepBuilderFactory.get("evenStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("evenStep");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public JobExecutionDecider myDecider(){
        return new MyDecider();
    }

    @Bean
    public Job flowDecisonDemoJob(){
        return jobBuilderFactory.get("flowDecisonDemoJob").start(firstStep())
                .next(myDecider())
                .from(myDecider()).on("EVEN").to(evenStep())
                .from(myDecider()).on("ODD").to(oddStep())
                .from(oddStep()).on("*").to(myDecider())
                .end()
                .build();
    }


}
