package com.et.model.order;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.et.model.DbUtils;

/**
 * 订单
 * @author Administrator
 *
 */
public class MyOrder {
	public Integer getOrderId(){
		String sql="(select nvl(max(orderid),0)+1 as myId from FOODORDER)";
		List<Map> result=DbUtils.query(sql);
		return Integer.parseInt((result.get(0).get("MYID").toString()));
	}
	/**
	 * 添加到数据库
	 */
	public Integer saveOrder(String deskId,int state,Integer sum){
		Integer orderId=getOrderId();
		String sql="insert into FOODORDER values('"+orderId+"','"+deskId+"',sysdate,'"+state+"','"+sum+"')";
		try {
			DbUtils.execute(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return orderId;
	}
	/**
	 * 详细订单
	 */
	public void saveOrderDetail(Integer orderId,Integer foodId,Integer count){
			String sql="insert into FOODORDERDETAIL values((select nvl(max(detailid),0)+1 as myId from FOODORDERDETAIL),'"+orderId+"','"+foodId+"','"+count+"')";
			try {
				DbUtils.execute(sql);
			} catch (SQLException e) {
		
				e.printStackTrace();
			}
		}
	/*
	 * 总价
	 */
	public Integer getZong(Integer orderId){
		String sql="select sum(gp) from (select gcount*price as gp from FOODORDERDETAIL t inner join food f on t.foodid=f.foodid where orderid="+orderId+") ";
		Integer integer=null;
		try {
			integer=DbUtils.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return integer;
	}
	
}
