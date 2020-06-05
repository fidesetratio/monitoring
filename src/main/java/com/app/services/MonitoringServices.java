package com.app.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.MonitoringDao;
import com.app.model.ColumnGroup;
import com.app.model.ColumnVariable;
import com.app.model.ProductCategory;
import com.app.model.ProductSearching;
import com.app.model.SearchEngine;

@Service
public class MonitoringServices {
	@Autowired
	private SqlSession monitoringsqlSession;
	
	public SearchEngine searchcategory(Long id){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return dao.searchcategory(id);
	}
	
	
	public void updateSearchEngine(SearchEngine engine){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.updateSearchEngine(engine);
	}
	public void updateColumVariable(ColumnVariable engine){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.updateColumVariable(engine);
	}
	
	public void updateStatusZero(Long cgid){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.updateStatusZero(cgid);
	}
	
	public void updateStatusOne(Long cid){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.updateStatusOne(cid);
	}
	
	
	public void insertSearchEngine(SearchEngine engine){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.insertSearchEngine(engine);
	}
	
	public void insertProductCategory(ProductCategory engine){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.insertProductCategory(engine);
	}
	
	public void insertProductSearching(ProductSearching productSearching){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.insertProductSearching(productSearching);
	}
	
	public void insertColumnGroup(ColumnGroup engine){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.insertColumnGroup(engine);
	}
	public void insertColumnVariable(ColumnVariable engine){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.insertColumnVariable(engine);
	}
	
	
	
	public void deleteSearchEngine(Long id){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.deleteSearchEngine(id);
	}
	
	public void deleteColumnVariable(Long cid){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.deleteColumnVariable(cid);
	}
	public void deleteProductSearching(Long id){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		dao.deleteProductSearching(id);
	}
	
	public List<SearchEngine> allsearchcategory(){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (List<SearchEngine>)dao.allsearchcategory();
	}
	
	public List<ProductCategory>searchcategoryproduct(){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (List<ProductCategory>)dao.searchcategoryproduct();
	};
	

	public List<ColumnGroup>selectcolumgroup(){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (List<ColumnGroup>)dao.selectcolumgroup();
	};
	

	public List<ColumnVariable>selectcolumvariablebygroupid(Long cgid){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (List<ColumnVariable>)dao.selectcolumvariablebygroupid(cgid);
	};
	public List<ColumnVariable>selectcolumvariablebygroupidAndActive(Long cgid){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (List<ColumnVariable>)dao.selectcolumvariablebygroupidAndActive(cgid);
	};
	public List<ProductSearching> searchproductbycat(Long cat_id){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (List<ProductSearching>)dao.searchproductbycat(cat_id);
	};
	public ColumnVariable selectcolumvariablebycolumnid(Long col_id){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return (ColumnVariable)dao.selectcolumvariablebycolumnid(col_id);
	} 
}
