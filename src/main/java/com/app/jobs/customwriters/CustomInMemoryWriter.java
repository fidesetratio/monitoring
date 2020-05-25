package com.app.jobs.customwriters;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CustomInMemoryWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("size::"+items.size());
		/*
		 * for(String s:items) { System.out.println("customInMemoryWriter"+s); }
		 */		
	}

}
