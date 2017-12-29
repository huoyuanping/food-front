package com.et.controller.food;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.et.model.PageTools;
import com.et.model.food.MyFood;
import com.et.model.foodtype.MyFoodType;


public class ShowFoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ShowFoodServlet() {
        super();
        
    }

	MyFoodType myf=new MyFoodType();
	MyFood mf=new MyFood();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	//第一次进如时没有菜系 查询所有
		//点击菜系传入菜系编号
		//定义变量
		String typeId="";
		//判断是否是点击菜系进入
		if(request.getParameter("typeId") != null){
			typeId=request.getParameter("typeId");
		}
		//获取session对象 将当前点击的餐桌编号存入session中方便后面结账
		HttpSession session=request.getSession();
		if(request.getParameter("deskId")!=null){
			//共享数据
			session.setAttribute("deskId", request.getParameter("deskId"));
		}
		System.out.println(typeId);
		List<Map> list=myf.getFoodTypeAll();
		//菜系
		//request.setAttribute("foodTypeList", list);
		//获取当前页
		String curPage=request.getParameter("curPage");
		String foodName=request.getParameter("foodName");
	
		Integer cur=1;
		if(curPage!=null){
			cur=Integer.parseInt(curPage);
		}
		if(foodName==null){
			foodName="";
		}
		
		//查询所有菜品
		PageTools pt=mf.getPage(cur, typeId, foodName);
		request.setAttribute("foodList", pt);
		request.getRequestDispatcher("/detail/caidan.jsp").forward(request, response); 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
