package com.app.jobs.search;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.SearchEngine;
import com.app.services.CommonServices;
import com.app.services.MonitoringServices;
import com.app.utils.QueryOracle;

@Component
public class SearchMemoryReader implements ItemReader<Map> {
	private int next;
	private List<Map> list;
	private int start;
	private int limit=100;
	
	@Autowired
	private CommonServices commonService;
	
	@Autowired
	private MonitoringServices monitoringService;
	
	@Override
	public Map read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		Map nextt = null;
		if(next < list.size()) {
			nextt = list.get(next);
			next++;
		}
		return nextt;
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		next = 0;
		start = stepExecution.getJobExecution().getExecutionContext().getInt("start");
		limit =  stepExecution.getJobExecution().getExecutionContext().getInt("limitsearch");
		String queryexecuted = stepExecution.getJobExecution().getExecutionContext().getString("queryexecuted");
		String q = QueryOracle.paging(queryexecuted, start, start+limit);
		list  =	 commonService.queryresult(q);
		if(list.size()<=0) {
			System.out.println("before step execution with size="+list.size());
					
		}
	}


}
