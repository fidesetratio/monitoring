package com.app.jobs.customwriters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class CustomInMemoryReader implements ItemReader<String>,StepExecutionListener {
	
	private int next;
	private List<String> list;
	public CustomInMemoryReader() {
		
	}
	
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		String nextt = null;
		if(next < list.size()) {
			nextt = list.get(next);
			next++;
		}
		return nextt;
		
	}

	
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("step execution");
		next = 0;
		int number = stepExecution.getJobExecution().getExecutionContext().getInt("number");
		list = new ArrayList<String>();
		for(int i=0;i<number;i++) {
			list.add(Integer.toString(i));
		};
		System.out.println("begin:"+next);
		
	}


	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		System.out.println("after step");
		return null;
	}

}
