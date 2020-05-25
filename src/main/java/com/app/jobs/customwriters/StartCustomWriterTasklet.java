package com.app.jobs.customwriters;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class StartCustomWriterTasklet implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start custom writer tasklet");
		chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .putInt("total", new Integer(1000));
		chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .putInt("start", new Integer(0));
		
		chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .put("totalComplete", new Double(1000));
		
		chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .putInt("number", new Integer(10));
		
		return RepeatStatus.FINISHED;
	}

}
