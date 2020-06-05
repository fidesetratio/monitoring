package com.app.dao;

import java.util.List;

import com.app.model.ColumnGroup;
import com.app.model.ColumnVariable;
import com.app.model.ProductCategory;
import com.app.model.ProductSearching;
import com.app.model.SearchEngine;

public interface MonitoringDao {
	
		public SearchEngine searchcategory(Long id);
		public List<ProductSearching> searchproductbycat(Long cat_id);
		public List<SearchEngine>allsearchcategory();
		public List<ProductCategory>searchcategoryproduct();
		public List<ColumnGroup> selectcolumgroup();
		public List<ColumnVariable> selectcolumvariablebygroupid(Long cgid);

		public List<ColumnVariable> selectcolumvariablebygroupidAndActive(Long cgid);
		public ColumnVariable selectcolumvariablebycolumnid(Long cgid);
		public void updateSearchEngine(SearchEngine engine);
		public void updateColumVariable(ColumnVariable engine);
		public void updateStatusZero(Long cgid);
		public void updateStatusOne(Long cgid);
		public void insertSearchEngine(SearchEngine engine);
		public void insertColumnVariable(ColumnVariable engine);
		public void insertColumnGroup(ColumnGroup engine);
		public void insertProductCategory(ProductCategory engine);
		public void deleteSearchEngine(Long id);
		public void deleteProductSearching(Long id);
		public void deleteColumnVariable(Long cid);
		public void insertProductSearching(ProductSearching productSearching);
		
		
}
