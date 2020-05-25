package com.app.jobs.looping;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class StartTasklet   implements Tasklet {

	private AtomicInteger counter = new AtomicInteger(0);
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("koq bisa ya ");
		String flow = "PRET";
		if(counter.addAndGet(1) >= 3) {
			flow = "FINISH";
			
		}
		
		chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .put("patar", flow);
		
		
		
		chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .put("total", (double)3);
		
		return RepeatStatus.FINISHED;
	}

}
