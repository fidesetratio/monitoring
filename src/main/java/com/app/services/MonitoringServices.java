package com.app.services;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.MonitoringDao;
import com.app.model.SearchEngine;

@Service
public class MonitoringServices {
	@Autowired
	private SqlSession monitoringsqlSession;
	
	public SearchEngine searchcategory(Long id){
		MonitoringDao dao=monitoringsqlSession.getMapper(MonitoringDao.class);
		return dao.searchcategory(id);
	}
}
