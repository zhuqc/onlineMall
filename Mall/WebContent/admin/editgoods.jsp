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
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<script type="text/javascript" src="js/goods.js" charset="utf-8"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<link href="css/four.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function selimage(){
window.open("<%=basePath%>saveimage.jsp","","toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes,copyhistory=no,scrollbars=yes,width=400,height=240,top="+(screen.availHeight-240)/2+",left="+(screen.availWidth-400)/2+"");}
</script>
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
			<td bgcolor="#FFFFFF"><form action="goods/updateGoods.action"
					name="myform" onsubmit="return check()">
					<table width="80%" border="0" align="center" cellpadding="4"
						cellspacing="1" bgcolor="#aec3de">
						<tr align="center" bgcolor="#F2FDFF">
							<td align="left" colspan="2" class="optiontitle">修改零食商品<input
								type="hidden" name="goodsid" value="${goods.goodsid}" /><input type="hidden" name="sellnum" id="sellnum"
							value="${goods.sellnum}" />
						<input type="hidden" name="addtime" id="addtime"
							value="${goods.addtime}" />
						<input type="hidden" name="hits" id="hits" value="${goods.hits}" />
						<input type="hidden" name="status" id="status"
							value="${goods.status}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">商品名称</td>
							<td align="left"><input type="text" name="goodsname"
								style="width: 160px" id="goodsname" value="${goods.goodsname}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">图片</td>
							<td align="left"><input type="text" name="image"
								style="width: 160px" id="image" value="${goods.image}"
								onclick="selimage();" readonly="readonly" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">零食类型</td>
							<td align="left"><select name="cateid" style="width: 160px"
								id="cateid"><c:forEach items="${cateList}" var="cate">
										<option value="${cate.cateid}">${cate.catename }</option>
									</c:forEach></select></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">市场价</td>
							<td align="left"><input type="text" name="marketprice"
								style="width: 160px" id="marketprice"
								value="${goods.marketprice}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">本站价</td>
							<td align="left"><input type="text" name="price"
								style="width: 160px" id="price" value="${goods.price}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">是否推荐</td>
							<td align="left"><input type="radio" name="recommend"
								id="recommend" value="是" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;<input
								type="radio" name="recommend" id="recommend" value="否" />否</td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">是否特价</td>
							<td align="left"><input type="radio" name="special"
								id="special" value="是" checked="checked" />是&nbsp;&nbsp;&nbsp;&nbsp;<input
								type="radio" name="special" id="special" value="否" />否</td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">库存数</td>
							<td align="left"><input type="text" name="storage"
								style="width: 160px" id="storage" value="${goods.storage}" /></td>
						</tr>
						
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">重量</td>
							<td align="left"><input type="text" name="weights"
								style="width: 160px" id="weights" value="${goods.weights}" /></td>
						</tr>
						<tr align="center" bgcolor="#F2FDFF">
							<td width="20%" align="right">介绍</td>
							<td align="left"><script type="text/javascript"
									src="ckeditor/ckeditor.js"></script>
								<textarea cols="80" name="contents" id="contents" rows="10">${goods.contents}</textarea>
								<script type="text/javascript">
									CKEDITOR.replace('contents', {
										language : 'zh-cn'
									});
								</script></td>
						</tr>
						<tr align="center" bgcolor="#ebf0f7">
							<td colspan="2"><input type="submit" name="Submit"
								value="提交" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset"
								name="Submit2" value="重置" /></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
</body>
</html>