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

import com.app.model.ColumnVariable;
import com.app.model.ProductSearching;
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
		
		   String startDate = (String)chunkContext.getStepContext().getJobParameters().get("startdate");
		   String endDate = (String)chunkContext.getStepContext().getJobParameters().get("enddate");
		   Long catprodid = (Long)chunkContext.getStepContext().getJobParameters().get("catprodid");
		   String namafile = (String)chunkContext.getStepContext().getJobParameters().get("namafile");
		   Long cgid = (Long)chunkContext.getStepContext().getJobParameters().get("cgid");
		   Long queryId = (Long)chunkContext.getStepContext().getJobParameters().get("queryId");

		   System.out.println("queryId="+queryId);	  
		   SearchEngine engine = monitoringService.searchcategory(queryId);
		   
		   List<ProductSearching> productSearchings = monitoringService.searchproductbycat(catprodid);
		   
		   String queryProduct = "";
		   String concatColumnQuery = "";
		   if(productSearchings.size()>0)
		   queryProduct = QueryOracle.getQueryBasedProduct(productSearchings);
		   
			  
		   String qt = engine.getQuery();
		   
		   

			List<ColumnVariable> columnVariable = monitoringService.selectcolumvariablebygroupidAndActive(cgid);
			System.out.println("columnVariable.size()=="+columnVariable.size());
			for(ColumnVariable v:columnVariable) {
				try {
				System.out.println(v.getVariable_name()+" =="+v.getQuery());
				qt = qt.replace(v.getVariable_name().trim(), v.getQuery().trim());
				System.out.println("qt:"+qt);
				}catch(Exception e) {
					break;
				}
			};
			
			if(columnVariable.size()>0) {
				concatColumnQuery =  QueryOracle.getQueryConcatColumn(columnVariable);
			};
			
		    if(!queryProduct.equals(""))
		    qt = qt.replace("##PRODUCTS_ID##", " "+queryProduct+" ");
			qt = qt.replace("###STARTDATE###", "'"+startDate+"'");
			qt = qt.replace("###ENDDATE###", "'"+endDate+"'");
			qt = qt.replace("##CONCAT_COLUMN_QUERY##", concatColumnQuery);
			
			
		 String queryHeader = QueryOracle.getOne(qt);
		 List<Map> l =	 commonService.queryresult(QueryOracle.total(qt));
		 List<Map> h =  commonService.queryresult(queryHeader);
		 
		 System.out.println("headersquery:"+queryHeader);
		 List<String> headers = new ArrayList<String>();
		 for(Map m:h) {
			 for(String hd:(Set<String>)m.keySet()) {
				 System.out.println("header:"+hd);
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
	        .putString("namafile",namafile);
		 
		 chunkContext
	        .getStepContext()
	        .getStepExecution()
	        .getJobExecution()
	        .getExecutionContext()
	        .putString("queryexecuted", qt);
			 
		 
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
