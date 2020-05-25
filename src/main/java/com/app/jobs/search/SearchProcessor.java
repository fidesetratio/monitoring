package com.app.jobs.search;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
@Component
public class SearchProcessor implements ItemProcessor<Map,Map>{

	@Override
	public Map process(Map item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}
	

}
