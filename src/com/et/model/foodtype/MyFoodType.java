package com.et.model.foodtype;

import java.util.List;
import java.util.Map;

import com.et.model.DbUtils;




public class MyFoodType {
	
	public List<Map> getFoodTypeAll(){
		String sql="select * from FOODTYPE";
		return DbUtils.query(sql);
	}
	public List<Map> getFoodAll(){
		String sql="select * from FOOD";
		return DbUtils.query(sql);
	}
}
