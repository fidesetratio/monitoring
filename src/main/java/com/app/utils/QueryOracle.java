package com.app.utils;

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
}
