package com.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.SearchEngine;
import com.app.services.CommonServices;
import com.app.services.MonitoringServices;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private CommonServices commonService;
	
	@Autowired
	private MonitoringServices monitoringService;
	
	
	@RequestMapping("/engine")
    public ModelAndView engine(ModelAndView m,HttpServletRequest request){
		 Long engineId  = ServletRequestUtils.getLongParameter(
	        request, "engineid", new Long(1));
		
		
		 SearchEngine engine = monitoringService.searchcategory(engineId);
		 try {
			 List<Map> l =	 commonService.queryresult(engine.getQuery());
			 Map h = (Map)l.get(0);
			 List<String> headers = new ArrayList<String>();
			 for(String t:(Set<String>)h.keySet()) {
				headers.add(t);
			 }
			 StringBuilder builder = new StringBuilder();
			 for(Map m2:l) {
				 builder.append("<tr>");
				 for(String t:(Set<String>)h.keySet()) {
				//	 System.out.println(m2.get(t));
					 builder.append("<td>").append(m2.get(t)).append("</td>");
				 }
				 builder.append("</tr>");
			 }
			 m.addObject("body", builder.toString());
			 m.addObject("headers", headers);
			 
			 
		 }catch(Exception e) {
			 
		 }
		 
        m.setViewName("home");
        return m;
    }
	

	

}
