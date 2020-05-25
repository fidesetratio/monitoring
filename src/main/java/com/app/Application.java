package com.app;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.app.model.SearchEngine;
import com.app.services.CommonServices;
import com.app.services.MonitoringServices;

@SpringBootApplication
@Configuration
@EnableBatchProcessing
@ImportResource("classpath:boot.xml")
public class Application  implements CommandLineRunner {

	@Autowired
	private CommonServices commonService;

	
	@Autowired
	private MonitoringServices monitoringService;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * SearchEngine e = monitoringService.searchcategory(new Long(1));
		 * System.out.println(e.getCategory_name()); List<Map> l =
		 * commonService.queryresult(e.getQuery()); for(Map m:l) {
		 * System.out.println("The set is: " + m.keySet()); for(String
		 * t:(Set<String>)m.keySet()) { System.out.println("t:"+t); } }
		 */
	}

}
