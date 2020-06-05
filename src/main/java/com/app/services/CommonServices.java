package com.app.services;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CommonDao;
import com.app.model.DetailProduct;

@Service
public class CommonServices {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Map> queryresult(String query){
		CommonDao dao=sqlSession.getMapper(CommonDao.class);
		return (List<Map>)dao.queryresult(query);
	}
	
	public DetailProduct selectproductutamabyregspaj(String reg_spaj){
		CommonDao dao=sqlSession.getMapper(CommonDao.class);
		return (DetailProduct)dao.selectproductutamabyregspaj(reg_spaj);
	}
	
	public List<DetailProduct> selectproductrelevant(String lsbs_id){
		CommonDao dao=sqlSession.getMapper(CommonDao.class);
		return (List<DetailProduct>)dao.selectproductrelevant(lsbs_id);
	}
}
