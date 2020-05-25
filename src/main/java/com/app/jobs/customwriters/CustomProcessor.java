package com.app.jobs.customwriters;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomProcessor implements ItemProcessor<String,String>{

	@Override
	public String process(String item) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("custom processor:"+item);
		return item;
	}

}
