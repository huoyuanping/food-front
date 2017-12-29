package com.et.controller.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.et.model.order.MyOrder;


/**
 * 放入餐车的类
 * @author Administrator
 *
 */
public class ClientCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ClientCartServlet() {
        super();
        
    }
	MyOrder mo=new MyOrder();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String flag=request.getParameter("flag");
		PrintWriter pw=response.getWriter();
		//判断session中是否存在菜品
		HttpSession session=request.getSession();
		//删除餐车数据
		if("delete".equals(flag)){
			String foodId=request.getParameter("foodId");
			session.removeAttribute(foodId);
			int sum = getSum(request);
			session.setAttribute("sum", sum);
			request.getRequestDispatcher("/detail/clientCart.jsp").forward(request, response);
		}else if("order".equals(flag)){//餐车下单
			//从session中拿出餐桌编号
			String deskId=session.getAttribute("deskId").toString();
			//状态
			int state=0;
			Integer sum=(Integer)session.getAttribute("sum");
			Integer orderId=mo.saveOrder(deskId, state,sum);
			//从session中循环所有的键
			Enumeration<String> keyS=session.getAttributeNames();
			while (keyS.hasMoreElements()) {
				String key =keyS.nextElement();
				if(key.startsWith("cart_")){
					String foodId=key.split("cart_")[1];
					//获取session中值
					Map map=(Map)session.getAttribute(key);
					Integer count=(Integer)map.get("count");
					mo.saveOrderDetail(orderId, Integer.parseInt(foodId), count);
				}
			}
			//清除session中的数据
			session.invalidate();
			//跳转到首页
			pw.write("<script>alert('下单成功')</script>");
			response.setHeader("refresh","1;url="+request.getContextPath()+"/ShowDeskServlet");
			
		}else {
			//当前的菜品id
			String foodId=request.getParameter("foodId");
			String foodName=request.getParameter("foodName");
			String price=request.getParameter("price");
			//当前菜品第一次加入餐车 session不存在 添加改菜品到session中数量为1
			
			
			Object food=session.getAttribute("cart_"+foodId);
			//第一次被加入
			if(food==null){
				//创建一个map对象 
				Map map=new HashMap();
				map.put("foodName", foodName);
				map.put("price", price);
				map.put("count", 1);
			
				//存入session中
			
				session.setAttribute("cart_"+foodId, map);
			}else {
				Map map=(Map)food;
				//第二次将数量加1
				Integer in=(Integer)map.get("count");
				map.put("count", in+1);
				//分布式环境
				session.setAttribute("cart_"+foodId, map);
			}
			
			int sum = getSum(request);
			session.setAttribute("sum", sum);
			
			request.getRequestDispatcher("/detail/clientCart.jsp").forward(request, response);
		}
		
	}

	
	private int getSum(HttpServletRequest request) throws ServletException {
		HttpSession session=request.getSession();
		Enumeration en = session.getAttributeNames();
		int sum = 0;
		while(en.hasMoreElements()){
			String key = (String) en.nextElement();
			if(key.startsWith("cart_")){
				Map map = (Map) session.getAttribute(key);
				int count = Integer.parseInt(map.get("count").toString());
				int priceTmp = Integer.parseInt(map.get("price").toString());
				sum = sum+count*priceTmp;
			}	
		}
		return sum;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
