package com.et.model.desk;

import java.util.List;
import java.util.Map;

import com.et.model.DbUtils;



/**
 * ������
 * @author Administrator
 *
 */
public class MyDesk {
	
	
	/**
	 * ��ѯ���еĲ���
	 * @return
	 */
	public List<Map> getPageAll(){
		String sql="select * from desk";
		List<Map> result=DbUtils.query(sql);
		return result;
	}
	
}
