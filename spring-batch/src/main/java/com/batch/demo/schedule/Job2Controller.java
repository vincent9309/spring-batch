package com.batch.demo.schedule;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("job2")
public class Job2Controller {

    @Autowired
    private JobOperator jobOperator;

    @GetMapping("/{job2param}")
    public String runJob1(@PathVariable("job2param") String job2param) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException {
        System.out.println("Request to run job2 with param: " + job2param);

        jobOperator.start("jobOperatorDemoJob","job2param="+job2param);

        return "Job2 success.";

    }
}
