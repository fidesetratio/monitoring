package com.app.jobs.search;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

@Component
public class SearchMemoryWriter extends AbstractItemStreamItemWriter<Map>  {
	
	private final String file = "data.xls";
	private HSSFWorkbook wb;
	private WritableResource resource;
	int row;
	private StepExecution stepExecution;
	private List<String> headers;
	int cursor;
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		System.out.println("closing......");
		 if (wb == null) {
		        return;
		    }
		   createFooterRow();
		    try (BufferedOutputStream bos = new BufferedOutputStream(resource.getOutputStream())) {
		        wb.write(bos);
		        bos.flush();
		        wb.close();
		    } catch (IOException ex) {
		        throw new ItemStreamException("Error writing to output file", ex);
		    }
		    row = 0;
		super.close();
	}


	@Override
	public void open(ExecutionContext executionContext) {
		// TODO Auto-generated method stub
		System.out.println("opening......");
		String namafile  = stepExecution
		        .getJobExecution()
		        .getExecutionContext().getString("namafile");
		
		System.out.println("nama fie:"+namafile);
		 wb = new HSSFWorkbook();
		 resource = new FileSystemResource(namafile);
		 boolean dont = false;
		 if(resource.exists()) {
			 dont = true;
			 try {
				wb= new HSSFWorkbook(resource.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 HSSFPalette palette = wb.getCustomPalette();
		 HSSFSheet s = wb.createSheet();
	
		 row = 0;
		
		 headers = (ArrayList<String>)	stepExecution
			        .getJobExecution()
			        .getExecutionContext().get("headers");
		 cursor = stepExecution
			        .getJobExecution()
			        .getExecutionContext().getInt("cursor");
		 if(headers == null) {
			  System.out.println("nullllll");
		 }
		 
		 if(!dont) {
		 createTitleRow(s, palette);
		    createHeaderRow(s,headers);
		 }
		super.open(executionContext);
		
		
	}
	
	private void createTitleRow(HSSFSheet s, HSSFPalette palette) {
        HSSFColor redish = palette.findSimilarColor((byte) 0xE6, (byte) 0x50, (byte) 0x32);
        palette.setColorAtIndex(redish.getIndex(), (byte) 0xE6, (byte) 0x50, (byte) 0x32);
 
        CellStyle headerStyle = wb.createCellStyle();
              HSSFRow r = s.createRow(row);
 
        Cell c = r.createCell(0);
        c.setCellValue("Internal Use Only");
        r.createCell(1).setCellStyle(headerStyle);
        r.createCell(2).setCellStyle(headerStyle);
        s.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        c.setCellStyle(headerStyle);
        row++;
    };
    
    private void createHeaderRow(HSSFSheet s, List<String> headers) {
        CellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
      
        HSSFRow r = s.createRow(row);
        r.setRowStyle((HSSFCellStyle) cs);
    
        Cell c = r.createCell(0);
        
        int j = 0;
        for(String h:headers) {
        	c = r.createCell(j);
        	c.setCellValue(h); 
        	s.setColumnWidth(j, poiWidth(24.0));
        	j++;
        
        }
    	row++;
		/*
		 * c.setCellValue("Author"); s.setColumnWidth(0, poiWidth(18.0)); c =
		 * r.createCell(1); c.setCellValue("Book Name"); s.setColumnWidth(1,
		 * poiWidth(24.0)); c = r.createCell(2); c.setCellValue("ISBN");
		 * s.setColumnWidth(2, poiWidth(18.0)); c = r.createCell(3);
		 * c.setCellValue("Price"); s.setColumnWidth(3, poiWidth(18.0));
		 */
      
    }
    
    private int poiWidth(double width) {
        return (int) Math.round(width * 256 + 200);
    }

    
    private void createFooterRow() {
        if(wb != null) {
    	HSSFSheet s = wb.getSheetAt(0);
        HSSFRow r = s.createRow(row);
        row++;
        };
     
    }
	
	
	

	private int size;

	@Override
	public void write(List<? extends Map> items) throws Exception {
		// TODO Auto-generated method stub
		size = items.size();
		System.out.println("SearchMemoryWriter size::"+items.size());
				HSSFSheet s = wb.getSheetAt(0);
				 int k = 0;
				 if(cursor != 0) {
					 row = cursor;
				 }
			    for (Map o : items) {
			    	k=0;
		
			    	System.out.println("rownya apa"+row);
			    	Row r = s.createRow(row);
			    	   
			    	for(String h:headers) {
			     
			        Cell c = r.createCell(k);
			        Object t = o.get(h);
			        String v = "";
			        if(t instanceof java.sql.Timestamp) {
			        	v = (( java.sql.Timestamp)t).toGMTString();
			        }else if(t instanceof String) {
			        	v = (String) t;
			        }else if (t instanceof Double) {
			        	v = Double.toString((Double)t);
			        }else if(t instanceof Integer) {
			        	v = Integer.toString((Integer)t);
				    }else if(t instanceof BigDecimal) {
				    	v = ((BigDecimal)t).toString();
				    }
			       
			        if(t == null) {
			        	c.setCellValue(v);
			        }else {
			        c.setCellValue(v);
			        }
			        k++;
			    	};
			    	row++;
			    }
			    
			    cursor = row;
			    stepExecution
				        .getJobExecution()
				        .getExecutionContext().putInt("cursor",cursor);
			    
	}

	
	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		System.out.println("after step execution");
		int currentsize = stepExecution.getJobExecution().getExecutionContext().getInt("currentsize");
		currentsize = currentsize + size;
		stepExecution.getJobExecution().getExecutionContext().putInt("currentsize",currentsize);
	
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
