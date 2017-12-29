package com.et.model.desk;

import java.util.List;
import java.util.Map;

import com.et.model.DbUtils;



/**
 * 餐桌类
 * @author Administrator
 *
 */
public class MyDesk {
	
	
	/**
	 * 查询所有的餐桌
	 * @return
	 */
	public List<Map> getPageAll(){
		String sql="select * from desk";
		List<Map> result=DbUtils.query(sql);
		return result;
	}
	
}
