package com.batch.demo.schedule;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

@Configuration
public class JobLaunchDemoJobConfiguration implements StepExecutionListener, ApplicationContextAware{
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    private Map<String, JobParameter> jobParams;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRegistry jobRegistry;

    private ApplicationContext context;

    @Autowired
    private JobParametersIncrementer myJobIncrementor;

    @Scheduled(fixedDelay = 5000)
    public void scheduler() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, JobParametersNotFoundException, NoSuchJobException {
        jobOperator().startNextInstance("jobScheduledDemoJob");
    }

    @Bean
    public Job jobScheduledDemoJob(){
        return jobBuilderFactory.get("jobScheduledDemoJob")
                .incrementer(myJobIncrementor)
                .start(jobScheduledDemoStep())
                .build();
    }

    @Bean
    public Step jobScheduledDemoStep() {
        return stepBuilderFactory.get("jobScheduledDemoStep")
                .listener(this)
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("jobScheduledDemoStep runs with param: " + jobParams.get
                            ("jobScheduledparam").getValue());
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistrar() throws Exception {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();

        postProcessor.setJobRegistry(jobRegistry);
        postProcessor.setBeanFactory(context.getAutowireCapableBeanFactory());
        postProcessor.afterPropertiesSet();

        return postProcessor;
    }



    @Bean
    public JobOperator jobOperator(){
        SimpleJobOperator operator = new SimpleJobOperator();

        operator.setJobLauncher(jobLauncher);
        operator.setJobParametersConverter(new DefaultJobParametersConverter());
        operator.setJobRepository(jobRepository);
        operator.setJobExplorer(jobExplorer);
        operator.setJobRegistry(jobRegistry);
        return operator;
    }

    @Bean
    public Job jobOperatorDemoJob(){
        return jobBuilderFactory.get("jobOperatorDemoJob")
                .start(jobOperatorDemoStep())
                .build();
    }

    @Bean
    public Step jobOperatorDemoStep() {
        return stepBuilderFactory.get("jobOperatorDemoStep")
                .listener(this)
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("jobOperatorDemoStep runs with param: " + jobParams.get
                            ("job2param").getValue());
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Job jobLaunchDemoJob(){
        return jobBuilderFactory.get("jobLaunchDemoJob")
                .start(jobLaunchDemoStep())
                .build();
    }

    @Bean
    public Step jobLaunchDemoStep() {
        return stepBuilderFactory.get("jobLaunchDemoStep")
                .listener(this)
                .tasklet(((contribution, chunkContext) -> {
                    System.out.println("jobLaunchDemoStep runs with param: " + jobParams.get
                            ("job1param").getValue());
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        jobParams = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
