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
 * ����ͳ�����
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
		//�ж�session���Ƿ���ڲ�Ʒ
		HttpSession session=request.getSession();
		//ɾ���ͳ�����
		if("delete".equals(flag)){
			String foodId=request.getParameter("foodId");
			session.removeAttribute(foodId);
			int sum = getSum(request);
			session.setAttribute("sum", sum);
			request.getRequestDispatcher("/detail/clientCart.jsp").forward(request, response);
		}else if("order".equals(flag)){//�ͳ��µ�
			//��session���ó��������
			String deskId=session.getAttribute("deskId").toString();
			//״̬
			int state=0;
			Integer sum=(Integer)session.getAttribute("sum");
			Integer orderId=mo.saveOrder(deskId, state,sum);
			//��session��ѭ�����еļ�
			Enumeration<String> keyS=session.getAttributeNames();
			while (keyS.hasMoreElements()) {
				String key =keyS.nextElement();
				if(key.startsWith("cart_")){
					String foodId=key.split("cart_")[1];
					//��ȡsession��ֵ
					Map map=(Map)session.getAttribute(key);
					Integer count=(Integer)map.get("count");
					mo.saveOrderDetail(orderId, Integer.parseInt(foodId), count);
				}
			}
			//���session�е�����
			session.invalidate();
			//��ת����ҳ
			pw.write("<script>alert('�µ��ɹ�')</script>");
			response.setHeader("refresh","1;url="+request.getContextPath()+"/ShowDeskServlet");
			
		}else {
			//��ǰ�Ĳ�Ʒid
			String foodId=request.getParameter("foodId");
			String foodName=request.getParameter("foodName");
			String price=request.getParameter("price");
			//��ǰ��Ʒ��һ�μ���ͳ� session������ ��ӸĲ�Ʒ��session������Ϊ1
			
			
			Object food=session.getAttribute("cart_"+foodId);
			//��һ�α�����
			if(food==null){
				//����һ��map���� 
				Map map=new HashMap();
				map.put("foodName", foodName);
				map.put("price", price);
				map.put("count", 1);
			
				//����session��
			
				session.setAttribute("cart_"+foodId, map);
			}else {
				Map map=(Map)food;
				//�ڶ��ν�������1
				Integer in=(Integer)map.get("count");
				map.put("count", in+1);
				//�ֲ�ʽ����
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
