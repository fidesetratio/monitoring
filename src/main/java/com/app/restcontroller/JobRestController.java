package com.app.restcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.JobProgress;

@RestController
@RequestMapping("/job")
public class JobRestController {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private ApplicationContext context;
		
	
	@Autowired
	private JobExplorer explorer;
	
	
	@RequestMapping(value="/run",method = RequestMethod.GET)
	public String handle(HttpServletRequest request) {
		 String jobName  = ServletRequestUtils.getStringParameter(
			        request, "jobName", "helloworld");
		
		 String startdate  = ServletRequestUtils.getStringParameter(
			        request, "startdate", "01 Jan 2018");
		 String enddate  = ServletRequestUtils.getStringParameter(
			        request, "enddate", "01 Jan 2019");
		 Long catprodid  = ServletRequestUtils.getLongParameter(
			        request, "catprodid",new Long(1));
		 String namafile  = ServletRequestUtils.getStringParameter(
			        request, "namafile", "output.xls");
		 
		 Long cgid  = ServletRequestUtils.getLongParameter(
			        request, "cgid", new Long(1));
		 Long queryId  = ServletRequestUtils.getLongParameter(
			        request, "queryId", new Long(2));
		
		 Job job = (Job)context.getBean(jobName);
		
		
		
		Long jobId = new Long(0);
		  JobParameters jobParameters = new JobParametersBuilder()
				  	 .addLong("time",System.currentTimeMillis())
		             .addString("source", "Spring Boot")
		             .addString("startdate",startdate)
		             .addString("enddate", enddate)
		             .addString("namafile", namafile)
		             .addLong("cgid", cgid)
				     .addLong("catprodid", catprodid)
				     .addLong("queryId", queryId)
						
				     .toJobParameters();
		            try {
		            	JobExecution jobExecution =	jobLauncher.run(job, jobParameters);
		            	jobId = jobExecution.getJobInstance().getId();
		            } catch (JobExecutionAlreadyRunningException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JobRestartException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JobInstanceAlreadyCompleteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JobParametersInvalidException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		return Long.toString(jobId);
	}

	@RequestMapping(value="/status",method = RequestMethod.GET)
	public BatchStatus status(HttpServletRequest request) {
		 Long jobId  = ServletRequestUtils.getLongParameter(
			        request, "jobId", new Long(0));
		 BatchStatus jobStatus = explorer.getJobExecution(jobId).getStatus();
		 return jobStatus;
		 
		    
	}
	

	@RequestMapping(value="/progress",method = RequestMethod.GET)
	public Integer progress(HttpServletRequest request) {
		 Long jobId  = ServletRequestUtils.getLongParameter(
			        request, "jobId", new Long(0));
		 JobExecution jobExecution = explorer.getJobExecution(jobId);
		 int nbItemsProcessed = -1;	
		 
		 if(jobExecution.getStepExecutions().size()>0)
		 { 		nbItemsProcessed = 0;	
			 for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
		 
	            nbItemsProcessed += stepExecution.getWriteCount();
	         }   
		 };
		 return nbItemsProcessed;
		 
		    
	}

	@RequestMapping(value="/statprogress",method = RequestMethod.GET)
	public JobProgress statprogress(HttpServletRequest request) {
		 Long jobId  = ServletRequestUtils.getLongParameter(
			        request, "jobId", new Long(0));
		 JobExecution jobExecution = explorer.getJobExecution(jobId);
		 double nbItemsProcessed = -1;
		 BatchStatus batchStatus = jobExecution.getStatus();
		 double jobComplete = (Double) (jobExecution.
                 getExecutionContext().
                 get("totalComplete") == null ? new Double(-1) : jobExecution.
                         getExecutionContext().
                         get("totalComplete"));
	
		 String completeAutomatic = (String)jobExecution.getExecutionContext().get("completeautomatis");
		 
		
		 
		 
		 JobProgress jobProgress = new JobProgress();
		 jobProgress.setStatus(batchStatus.name());
		 
		 if(jobExecution.getExecutionContext().get("namafile") != null) {
				jobProgress.setFilename((String)jobExecution.getExecutionContext().get("namafile"));
				 
		 }
		 

		 
		 
		 boolean isStepExecution=false;
		 if(jobExecution.getStepExecutions().size()>0)
		 { 		nbItemsProcessed = 0;	
		 isStepExecution = true;
			 for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
				
	            nbItemsProcessed += stepExecution.getWriteCount();
	         }   
		 };
		 jobProgress.setProgress(nbItemsProcessed);
		 jobProgress.setTotal(jobComplete);
		 double progress = Math.round(nbItemsProcessed / jobComplete * 100);
		 
		 
		 if(!isStepExecution )
		 {
			 progress = -1;
		 }
		 jobProgress.setPercentage(progress);
		 
			
		 if(completeAutomatic != null)
		 {
			 jobProgress.setProgress((double)100);
			 jobProgress.setTotal((double)100);
			 jobProgress.setPercentage((double)100);
				
		 }

		 return jobProgress;

		 
		 
		    
	}

}
