package com.app.jobs.search;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Component
public class SearchDecision implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		// TODO Auto-generated method stub
		Double totalComplete = (Double) stepExecution.getJobExecution().getExecutionContext().get("totalComplete");
		Integer start = stepExecution.getJobExecution().getExecutionContext().getInt("start");
		
		int currentsize = stepExecution.getJobExecution().getExecutionContext().getInt("currentsize");
		currentsize = currentsize+1;
		currentsize = total(stepExecution.getJobExecution());
		System.out.println("currentsize="+currentsize+" total="+totalComplete);
		int limit =  stepExecution.getJobExecution().getExecutionContext().getInt("limitsearch");
		
		
		if(currentsize < totalComplete) {
			 start=start+limit;	
			 stepExecution.getJobExecution().getExecutionContext().putInt("start", start);
			 return new FlowExecutionStatus("LOOP");
		}
		
		return new FlowExecutionStatus("FINISHIT");
	}
	
	
	
public int total(JobExecution jobExecution) {
	int nbItemsProcessed = 0;
	 if(jobExecution.getStepExecutions().size()>0)
	 { 		nbItemsProcessed = 0;	
	
		 for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
			
            nbItemsProcessed += stepExecution.getWriteCount();
         }   
	 };
	 
	 return nbItemsProcessed;
	 
}

}
