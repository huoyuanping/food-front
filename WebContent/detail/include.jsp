<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="dish_class">
	<div id="dish_top">
		<ul>
			<li class="dish_num"></li>
			<li><a href="clientOrderList.html"> <img
					src="${pageContext.request.contextPath}/detail/style/images/call2.gif" />
			</a></li>
		</ul>
	</div>

	<div id="dish_2">
	<jsp:useBean id="my" class="com.et.model.foodtype.MyFoodType"></jsp:useBean>
		<ul>
			<c:forEach var="tmp" items="${my.foodTypeAll}">
				<li><a
					href="${pageContext.request.contextPath}/ShowFoodServlet?typeId=${pageScope.tmp.TYPEID}">${pageScope.tmp.TYPENAME}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div id="dish_3">
		<!-- 搜索菜品表单  -->
		<form action="${pageContext.request.contextPath}/ShowFoodServlet" method="post">
			<table width="166px">
				<tr>
					<td><input type="text" id="dish_name" name="foodName" value="${param.foodName }"
						class="select_value" /> <input type="hidden" value="selectFood"
						name="method"></td>
				</tr>
				<tr>
					<td><input type="submit" id="sub" value="" /></td>
				</tr>
				<tr>
					<td><a href="#"> <img src="${pageContext.request.contextPath}/detail/style/images/look.gif" />
					</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>