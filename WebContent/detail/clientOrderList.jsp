
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/detail/style/css/index.css" />
	<script type="text/javascript">
		// 通知服务员结账
		function callPay(node) {
			var orderId = node.lang;
			window.location.href = "jiezhang.html";
		}
	</script>
</head>

<body style="text-align: center">
	<div id="all">
		<div id="menu">
			<!-- 餐车div -->
			<div id="count">
				<table align="center" width="100%">
					<tr height="40">
				 		<td align="center" width="20%">菜名</td>
				 		<td align="center" width="20%">单价</td>
				 		<td align="center" width="20%">数量</td>
				 		<td align="center" width="20%">小计</td>
				 	</tr>
					
					<c:forEach var="ss" items="${sessionScope }">
				 		<c:if test="${fn:startsWith(pageScope.ss.key,'cart_') }">
						<tr height="60">
						 		<td align="center" width="20%">${pageScope.ss.value.foodName}</td>
						 		<td align="center" width="20%">￥${pageScope.ss.value.price}</td>
						 		<td align="center" width="20%">${pageScope.ss.value.count}</td>
						 		<td align="center" width="20%">${pageScope.ss.value.price*pageScope.ss.value.count}</td>
					 	</tr>
					 	
						</c:if>
					</c:forEach>
					<tr>
						<td colspan="6" align="right">总计:
							<span style="font-size:36px;">&yen;</span>
							<label
								id="counter" style="font-size:36px">${sessionScope.sum}</label>
						</td>
					</tr>
					<tr>
						<td colspan="6" style="margin-left: 100px; text-align: center;"align="right">
							<input type="hidden" name="bId" value="">
							<input type="button" value="结账" class="btn_next" lang="" onclick="callPay(this)" />
						</td>
					</tr>
				</table>
			</div>
		</div>

		<!-- 右边菜系列表，菜品搜索框  -->
		<jsp:include page="include.jsp"></jsp:include>
		
	</div>
</body>
</html>
