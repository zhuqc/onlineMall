<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ taglib
	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/four.css" rel="stylesheet" type="text/css" />
</head>
<%
	String message = (String) request.getAttribute("message");
	if (message == null) {
		message = "";
	}
	if (!message.trim().equals("")) {
		out.println("<script language='javascript'>");
		out.println("alert('" + message + "');");
		out.println("</script>");
	}
	request.removeAttribute("message");
%><body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<td bgcolor="#FFFFFF"><table width="96%" border="0"
					align="center" cellpadding="4" cellspacing="1" bgcolor="#aec3de">
					<tr align="left" bgcolor="#F2FDFF">
						<td colspan="20" class="optiontitle">零食商品信息列表</td>
					</tr>
					<tr align="center">
						<td align="center" bgcolor="#ebf0f7">商品名称</td>
						<td align="center" bgcolor="#ebf0f7">零食类型</td>
						<td align="center" bgcolor="#ebf0f7">市场价</td>
						<td align="center" bgcolor="#ebf0f7">本站价</td>
						<td align="center" bgcolor="#ebf0f7">是否推荐</td>
						<td align="center" bgcolor="#ebf0f7">是否特价</td>
						<td align="center" bgcolor="#ebf0f7">库存数</td>
						<td align="center" bgcolor="#ebf0f7">销量</td>
						<td align="center" bgcolor="#ebf0f7">上架日期</td>
						<td align="center" bgcolor="#ebf0f7">点击数</td>
						<td align="center" bgcolor="#ebf0f7">状态</td>
						<td align="center" bgcolor="#ebf0f7">重量</td>
					</tr>
					<c:forEach items="${goodsList}" var="goods">
						<tr align="center" bgcolor="#FFFFFF">
							<td align="center">${goods.goodsname}</td>
							<td align="center">${goods.catename}</td>
							<td align="center">${goods.marketprice}</td>
							<td align="center">${goods.price}</td>
							<td align="center">${goods.recommend}</td>
							<td align="center">${goods.special}</td>
							<td align="center">${goods.storage}</td>
							<td align="center">${goods.sellnum}</td>
							<td align="center">${goods.addtime}</td>
							<td align="center">${goods.hits}</td>
							<td align="center">${goods.status}</td>
							<td align="center">${goods.weights}</td>
						</tr>
					</c:forEach>
					<tr align="right" bgcolor="#ebf0f7">
						<td colspan="20"><form action="goods/queryGoodsByCond.action"
								name="myform" method="post">
								查询条件<select name="cond" style="width: 100px"><option
										value="goodsname">按商品名称查询</option>
									<option value="cateid">按零食类型查询</option>
									<option value="marketprice">按市场价查询</option>
									<option value="price">按本站价查询</option>
									<option value="recommend">按是否推荐查询</option>
									<option value="special">按是否特价查询</option>
									<option value="storage">按库存数查询</option>
									<option value="sellnum">按销量查询</option>
									<option value="addtime">按上架日期查询</option>
									<option value="hits">按点击数查询</option>
									<option value="status">按状态查询</option>
									<option value="weights">按重量查询</option></select>关键字<input type="text"
									name="name" style="width: 100px" /><input type="submit"
									value="查询" />
							</form></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>