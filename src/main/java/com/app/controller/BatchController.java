package com.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.model.ColumnGroup;
import com.app.model.ColumnVariable;
import com.app.model.DetailProduct;
import com.app.model.ProductCategory;
import com.app.model.ProductSearching;
import com.app.model.SearchEngine;
import com.app.services.CommonServices;
import com.app.services.MonitoringServices;

@Controller
@RequestMapping("/batch")
public class BatchController {
	
	
	@Autowired
	private MonitoringServices monitoringServices;
	
	@Autowired
	private CommonServices commonServices;
	
	
	
	@RequestMapping("/monitor")
    public ModelAndView monitor(ModelAndView m,HttpServletRequest request){

		List<SearchEngine> list = monitoringServices.allsearchcategory();
		List<ProductCategory> listproductcategory = monitoringServices.searchcategoryproduct();
		List<ColumnGroup> listOfGroupColumn = monitoringServices.selectcolumgroup();
		
		 m.addObject("allquery", list);
		 m.addObject("prodcategory", listproductcategory);
		 m.addObject("groupcolumn", listOfGroupColumn);
				 
		 m.setViewName("batch");
	     return m;
	        
	}
	
	@RequestMapping("/query")
    public ModelAndView query(ModelAndView m,HttpServletRequest request,RedirectAttributes redirectAttributes){
		

		List<SearchEngine> list = monitoringServices.allsearchcategory();
		List<ProductCategory> listproductcategory = monitoringServices.searchcategoryproduct();
		List<ColumnGroup> listOfGroupColumn = monitoringServices.selectcolumgroup();
		 String action  = ServletRequestUtils.getStringParameter(
			        request, "action", "none");
		 Long id  = ServletRequestUtils.getLongParameter(
			        request, "id", 0);
		 
		 Long update  = ServletRequestUtils.getLongParameter(
			        request, "update", 0);
		 
		 Long updateadd  = ServletRequestUtils.getLongParameter(
			        request, "updateadd", 0);
		  
		 if(id>0) {
			 SearchEngine s = monitoringServices.searchcategory(id);
			 m.addObject("search", s);
			 
		 }else {
			 SearchEngine s = new SearchEngine();
			 m.addObject("search", s);
		 }
		 
		 if(action.equals("delete")) {
			 monitoringServices.deleteSearchEngine(id);
			 return new ModelAndView("redirect:/batch/query");
		 }
		 
		 if(update>0) {
			 String label  = ServletRequestUtils.getStringParameter(
				        request, "label", "none");
			 String query  = ServletRequestUtils.getStringParameter(
				        request, "query", "");
			 int active  = ServletRequestUtils.getIntParameter(
				        request, "active", 0);
			 SearchEngine engine = new SearchEngine();
			 engine.setId(id);
			 engine.setQuery(query);
			 engine.setLabel(label);
			 engine.setActive(active);
			 monitoringServices.updateSearchEngine(engine);
			 redirectAttributes.addFlashAttribute("message", "Successfully Edit");
			 
			 return new ModelAndView("redirect:/batch/query?id="+id+"&action=edit");
			 
			 
		 }
		 
		 if(updateadd>0) {
			 
			 String label  = ServletRequestUtils.getStringParameter(
				        request, "label", "none");
			 String query  = ServletRequestUtils.getStringParameter(
				        request, "query", "");
			 int active  = ServletRequestUtils.getIntParameter(
				        request, "active", 0);
			 SearchEngine engine = new SearchEngine();
			 engine.setQuery(query);
			 engine.setLabel(label);
			 engine.setActive(active);
			 monitoringServices.insertSearchEngine(engine);
			 redirectAttributes.addFlashAttribute("message", "Successfully Add");
			 return new ModelAndView("redirect:/batch/query");
			 
		 }
		 
		 m.addObject("allquery", list);
		 m.addObject("action", action);
		 m.addObject("prodcategory", listproductcategory);
		 m.addObject("groupcolumn", listOfGroupColumn);
				 
		 m.setViewName("query");
	     return m;
	        
	     
	     
	}
	
	@RequestMapping("/product")
    public ModelAndView product(ModelAndView m,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(required = false,name="idChecked") List<String> idchecked){
		
		Long cat_id  = ServletRequestUtils.getLongParameter(
		        request, "catid", 0);
		 String action  = ServletRequestUtils.getStringParameter(
			        request, "action", "none");
		 String reg_spaj  = ServletRequestUtils.getStringParameter(
			        request, "reg_spaj", "");
		 
		 String categoryname  = ServletRequestUtils.getStringParameter(
			        request, "categoryname", "");
		 
		 Long submitaddcategory  = ServletRequestUtils.getLongParameter(
			        request, "submitaddcategory", 0);
		 
		 Long prodid  = ServletRequestUtils.getLongParameter(
			        request, "prodid", 0);
		 
		 Long addcategory  = ServletRequestUtils.getLongParameter(
			        request, "addcategory", 0);
		 System.out.println("actionnya a:"+action);
		 
		 int optproduct = ServletRequestUtils.getIntParameter(request, "optproduct", 0);

		 int actionfrom = ServletRequestUtils.getIntParameter(request, "actionfrom", 0);
		 DetailProduct d = new DetailProduct();
		 List<DetailProduct> productRelevant = new ArrayList<DetailProduct>();
		 
		 if(submitaddcategory>0) {
			 ProductCategory en = new ProductCategory();
			 en.setCategory_description(categoryname);
			 en.setCategory_name(categoryname);
			 monitoringServices.insertProductCategory(en);
			 return new ModelAndView("redirect:/batch/product?catid=1");
		 }
			 if(cat_id>0) {
				
				 m.addObject("prods",  monitoringServices.searchproductbycat(cat_id));	 
			 }
		
			 if(action.equals("search")) {
				 if(optproduct==1) {
					 d = commonServices.selectproductutamabyregspaj(reg_spaj);
					 productRelevant =  commonServices.selectproductrelevant(Long.toString(d.getLsbs_id()));
					 final long lsbs_id = d.getLsbs_id();
					 final long lsdbs_number = d.getLsdbs_number();
					 productRelevant=	 productRelevant.stream().filter(dt -> ! (dt.getLsbs_id() ==  lsbs_id && dt.getLsdbs_number() == lsdbs_number )).collect(Collectors.toList());
				 };
			 }else if(action.equals("addproduct")){
				 
				 if(actionfrom == 1) {
					 
					 d = commonServices.selectproductutamabyregspaj(reg_spaj);
					 productRelevant =  commonServices.selectproductrelevant(Long.toString(d.getLsbs_id()));
					 final long lsbs_id = d.getLsbs_id();
					 final long lsdbs_number = d.getLsdbs_number();
					 productRelevant=	 productRelevant.stream().filter(dt -> ! (dt.getLsbs_id() ==  lsbs_id && dt.getLsdbs_number() == lsdbs_number )).collect(Collectors.toList());
			         List<ProductSearching> prods = monitoringServices.searchproductbycat(cat_id);
			         Map<String, ProductSearching> map = new HashMap<>();
			         for (ProductSearching prod : prods) {
			             map.put(Long.toString(prod.getLsbs_id())+"-"+Long.toString(prod.getLsdbs_number()), prod);
			         }
					 idchecked=idchecked.stream().filter(u-> ! map.containsKey(u)).collect(Collectors.toList());
					 idchecked.stream().forEach(u->{
						 String[] s=u.split("-");
						 ProductSearching p1 = new ProductSearching();
						 p1.setLsbs_id(Long.parseLong(s[0]));
						 p1.setLsdbs_number(Long.parseLong(s[1]));
						 p1.setCat_id(cat_id.intValue());
						 monitoringServices.insertProductSearching(p1);
						 
					 });
					 return new ModelAndView("redirect:/batch/product?catid="+cat_id);
				 }
			 }else if(action.equals("del")){
				  if(prodid>0)
				 	monitoringServices.deleteProductSearching(prodid);
				  
				  return new ModelAndView("redirect:/batch/product?catid="+cat_id);
			 }
		
			 
		
			 
		 List<ProductCategory> listproductcategory = monitoringServices.searchcategoryproduct();
		 m.addObject("catid",  cat_id);	
		 m.addObject("addcategory",  addcategory);	
	     m.addObject("prodcategory", listproductcategory);
	     m.addObject("detailprod",  d);	
	     m.addObject("productRelevant",  productRelevant);	
	     m.addObject("reg_spaj",  reg_spaj);	
		 m.setViewName("product");
	     return m;
	}
	@RequestMapping("/column")
    public ModelAndView column(ModelAndView m,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(required = false,name="idChecked") List<Long> idchecked){
		Long cat_id  = ServletRequestUtils.getLongParameter(
		        request, "catid", 0);
		
		Long addcategory  = ServletRequestUtils.getLongParameter(
		        request, "addcategory", 0);
		
		Long submitaddcategory  = ServletRequestUtils.getLongParameter(
		        request, "submitaddcategory", 0);
		Long updateaction  = ServletRequestUtils.getLongParameter(
		        request, "updateaction", 0);
		Long columnid  = ServletRequestUtils.getLongParameter(
		        request, "columnid", 0);
		String variable_name  = ServletRequestUtils.getStringParameter(
		        request, "variable_name", "");
		String column_name  = ServletRequestUtils.getStringParameter(
		        request, "column_name", "");
		
		String group_name  = ServletRequestUtils.getStringParameter(
		        request, "categoryname", "");
		 String action  = ServletRequestUtils.getStringParameter(
			        request, "action", "none");
		
		String query  = ServletRequestUtils.getStringParameter(
		        request, "query", "");
		
		Long cid  = ServletRequestUtils.getLongParameter(
		        request, "cid", 0);
		
		Long updatecolumn  = ServletRequestUtils.getLongParameter(
		        request, "updatecolumn", 0);
		
		String actiononecolumn = "";
		  List<ColumnGroup> listColumnGroup = monitoringServices.selectcolumgroup();
		  ColumnVariable oneColumn = new ColumnVariable();
		  if(cat_id>0) {
				
				 m.addObject("columns",  monitoringServices.selectcolumvariablebygroupid(cat_id));	 
			 }
		  
		  if(updatecolumn>0) {
			  
			  	monitoringServices.updateStatusZero(cat_id);
			  	idchecked.stream().forEach(u->{
			  		monitoringServices.updateStatusOne(u);
			  	});

				return new ModelAndView("redirect:/batch/column?catid="+cat_id);
		  }

		  if(columnid > 0) {
			  actiononecolumn = "Edit";
			  oneColumn = monitoringServices.selectcolumvariablebycolumnid(columnid);
		  }
		  
		  if(submitaddcategory>0) {
			  ColumnGroup columnGroup = new ColumnGroup();  
			  columnGroup.setGroup_name(group_name);
			  monitoringServices.insertColumnGroup(columnGroup);
			    
				return new ModelAndView("redirect:/batch/column?catid=1");
				
		  }
		  
		  if(action.equals("add")) {
			  actiononecolumn = "Add";
				  
		  }else if(action.equals("del")) {
			  monitoringServices.deleteColumnVariable(columnid);
				return new ModelAndView("redirect:/batch/column?catid="+cat_id);
				
		  }
		  
		  if(updateaction>0) {
			   if(updateaction==1) { // save
				   ColumnVariable v = new ColumnVariable();
				   v.setCid(cid);
				   v.setCgid(cat_id);
				   v.setColumn_name(column_name);
				   v.setQuery(query);
				   v.setVariable_name(variable_name);
				   monitoringServices.updateColumVariable(v);
					return new ModelAndView("redirect:/batch/column?catid="+cat_id+"&columnid="+cid+"&action=edit");
						 
			   }
			   if(updateaction == 2) {
				   ColumnVariable v = new ColumnVariable();
				   
				   v.setCgid(cat_id);
				   v.setColumn_name(column_name);
				   v.setQuery(query);
				   v.setVariable_name(variable_name);
				   monitoringServices.insertColumnVariable(v);
				   return new ModelAndView("redirect:/batch/column?catid="+cat_id+"&columnid=");
			   }
		  }
		  
		  oneColumn.setCgid(cat_id);
		  m.addObject("listColumnGroup", listColumnGroup);
		  m.addObject("catid",  cat_id);
		  m.addObject("addcategory",  addcategory);
		  m.addObject("actiononecolumn",actiononecolumn);
		  m.addObject("onecolum",  oneColumn);
		  m.setViewName("column");
	     return m;
	}
		
	
}
