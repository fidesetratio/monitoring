package com.app.jobs.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.model.SearchEngine;
import com.app.services.CommonServices;
import com.app.services.MonitoringServices;
import com.app.utils.QueryOracle;
@Component
public class StartInitSearchTasklet implements Tasklet{

	@Autowired
	private CommonServices commonService;
	
	@Autowired
	private MonitoringServices monitoringService;
	
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		
		 SearchEngine engine = monitoringService.searchcategory(new Long(1));
		 
		 
		 
		 List<Map> l =	 commonService.queryresult(QueryOracle.total(engine.getQuery()));
		 List<Map> h =  commonService.queryresult(QueryOracle.getOne(engine.getQuery()));
		 List<String> headers = new ArrayList<String>();
		 for(Map m:h) {
			 for(String hd:(Set<String>)m.keySet()) {
				 headers.add(hd);
			 }
		 }

		 
		 Map m = new HashMap();
		 if(l.size()>0) {
			 m = l.get(0);
		 }
		
		 
		 
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .putString("namafile","output.xls");
		 
		 
		 
		 chunkContext
        .getStepContext()
        .getStepExecution()
        .getJobExecution()
        .getExecutionContext()
        .put("totalComplete", ((BigDecimal)m.get("TOTAL")).doubleValue());
		 
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .putInt("start",new Integer(0));
		 
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .put("headers",headers);
		
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .putInt("currentsize",new Integer(0));
		 
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .putInt("cursor",new Integer(0));
		
		 
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .putInt("limitsearch",new Integer(200));
		return RepeatStatus.FINISHED;
	}
	

}
