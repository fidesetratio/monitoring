package com.app.utils;

import java.util.List;

import com.app.model.ColumnVariable;
import com.app.model.ProductSearching;

public class QueryOracle {
		public static String paging(String query, int start, int end) {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT outer.* FROM (SELECT ROWNUM rn, inner.* FROM ( ");
			builder.append(query);
			builder.append(" )");
			builder.append(" inner) outer ");
			builder.append("WHERE outer.rn > "+start+" AND outer.rn <= "+end);
			 System.out.println(builder.toString());
			 return builder.toString();
			 
		}
		
		public static String total(String query) {
			StringBuilder builder = new StringBuilder();
			builder.append("select count(*) as total from( ");
			builder.append(query);
			builder.append(" )");
			return builder.toString();
		}
		
		public static String getOne(String query) {
			StringBuilder builder = new StringBuilder();
			builder.append("select * from ( ");
			builder.append(query);
			builder.append(" )");
			builder.append(" where rownum = 1 ");
			return builder.toString();
		}
		
		public static String getQueryBasedProduct(List<ProductSearching> productSearchings) {
			StringBuilder builder = new StringBuilder();
			int number = 0;
			for(ProductSearching product:productSearchings) {
				builder.append("(pr.lsbs_id='"+product.getLsbs_id()+"'");
				builder.append(" and ");
				builder.append("pr.lsdbs_number='"+product.getLsdbs_number()+"') ");
				builder.append("or");
				number++;
			}
			
			if(number > 0) {
				int length = builder.toString().length();
				String temp = builder.toString().substring(0, length-2);
				builder = new StringBuilder();
				builder.append(temp);
			}
			
			
			return builder.toString();
		}
		
		public static String getQueryConcatColumn(List<ColumnVariable> columnsVariable) {
			StringBuilder builder = new StringBuilder();
			int number = 0;
			for(ColumnVariable product:columnsVariable) {
				builder.append(product.getQuery());
				builder.append(",");
				number++;
			}
			if(number > 0) {
				int length = builder.toString().length();
				String temp = builder.toString().substring(0, length-1);
				builder = new StringBuilder();
				builder.append(temp);
			}
			
			return builder.toString();
			
		}
			
}
