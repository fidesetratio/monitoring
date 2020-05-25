package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/batch")
public class BatchController {
	@RequestMapping("/monitor")
    public ModelAndView monitor(ModelAndView m,HttpServletRequest request){
		  m.setViewName("batch");
	     return m;
	        
	}
}
