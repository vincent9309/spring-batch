package com.batch.demo.errorHandling.overview;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ErrorHandlingOverviewJobConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public Tasklet errorHandlingOverviewTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();

                if(stepExecutionContext.containsKey("first")) {
                    System.out.println("Second run will success...");
                    return RepeatStatus.FINISHED;
                }
                else {
                    System.out.println("First run will fail...");
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put
                            ("first", true);
                    throw new RuntimeException("Error occurs...");
                }
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(errorHandlingOverviewTasklet())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(errorHandlingOverviewTasklet())
                .build();
    }

    @Bean
    public Job errorHandlingOverviewJob() {
        return jobBuilderFactory.get("errorHandlingOverviewJob")
                .start(step1())
                .next(step2())
                .build();
    }
}
