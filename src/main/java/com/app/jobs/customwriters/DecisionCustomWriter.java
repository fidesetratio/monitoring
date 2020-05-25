package com.app.jobs.customwriters;

import java.util.Random;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class DecisionCustomWriter implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		// TODO Auto-generated method stub
		System.out.println("decide...");
		Integer start = stepExecution.getJobExecution().getExecutionContext().getInt("start");
		Integer total = stepExecution.getJobExecution().getExecutionContext().getInt("total");
		
		
		if(start<total-10) {
			
			 Random ran = new Random(); 
			 stepExecution.getJobExecution().getExecutionContext().putInt("number", 10);
			 start=start+10;
			 stepExecution.getJobExecution().getExecutionContext().putInt("start", start);
			return new FlowExecutionStatus("LOOP");
		}
		
		
		
		return new FlowExecutionStatus("FINISHIT");
	}

}
