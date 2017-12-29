package com.et.model.food;

import java.util.List;
import java.util.Map;

import com.et.model.DbUtils;
import com.et.model.PageTools;




public class MyFood {
	/**
	 * 获取总条数
	 */
	public Integer getPageCount(String typeId,String foodName ){
		
		String sql="select count(rowid) as cr from FOOD where 1=1";
		if(!typeId.equals("")||typeId!=null){
			sql=sql+" and typeid like '%"+typeId+"%'";
		}
		if(!foodName.equals("")||foodName!=null){
			sql=sql+" and foodName like '%"+foodName+"%'";
		}
		List<Map> result=DbUtils.query(sql);
		return Integer.parseInt(result.get(0).get("CR").toString());
	}
	
	public PageTools getPage(Integer curPage,String typeId,String foodName){
		Integer totalCount=getPageCount(typeId,foodName);
		PageTools pt=new PageTools(curPage, totalCount, 6);
		String sql="select * from (select f.*,rownum rn from food f  where typeid like '%"+typeId+"%' and foodname like'%"+foodName+"%')where rn>="+pt.getStartIndex()+" and rn<="+pt.getEndIndex();
		List<Map> result=DbUtils.query(sql);
		pt.setData(result);
		return pt;
		
	
	}

	
}
