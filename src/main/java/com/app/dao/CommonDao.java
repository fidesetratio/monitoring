package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.model.DetailProduct;

public interface CommonDao {
	 public List<Map> queryresult(String query);
	 public DetailProduct selectproductutamabyregspaj(String reg_spaj);
	 public List<DetailProduct> selectproductrelevant(String lsbs_id);
	 
}
