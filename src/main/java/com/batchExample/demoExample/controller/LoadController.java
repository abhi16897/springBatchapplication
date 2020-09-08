package com.batchExample.demoExample.controller;

import java.util.HashMap;
import java.util.Map;



import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batchExample.demoExample.DemoExampleApplication;

@RestController
public class LoadController {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	@Qualifier("job1")
	Job job1;
	@Autowired
	@Qualifier("job2")
	Job job2;
	ApplicationContext context;
	
	
	@Autowired
	@Qualifier("job3")
	Job job3;
	@GetMapping("/load")
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps=new HashMap<String, JobParameter>();	
		// Job job=context.getBean("User-Detail",Job.class);
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameter=new JobParameters(maps);
		JobExecution jobExecution=jobLauncher.run(job1, parameter);
		return jobExecution.getStatus();
	}	
	@GetMapping("/send")
	public BatchStatus nice() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps=new HashMap<String, JobParameter>();	
		// Job job=context.getBean("User-Detail",Job.class);
		System.out.println("=================================================HEllo==================================================");
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameter=new JobParameters(maps);
		JobExecution jobExecution=jobLauncher.run(job2, parameter);
		return jobExecution.getStatus();
	}	
	@GetMapping("/insert")
	public BatchStatus post() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps=new HashMap<String, JobParameter>();	
		// Job job=context.getBean("User-Detail",Job.class);
		System.out.println("=================================================HEllo==================================================");
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameter=new JobParameters(maps);
		JobExecution jobExecution=jobLauncher.run(job3, parameter);
		return jobExecution.getStatus();
	}
}
