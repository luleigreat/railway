<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
	String context = request.getContextPath();
	url += context;
	application.setAttribute("ctx", url);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆实例</title>
</head>
<body>
	<h1>登陆实例</h1><a href="${ctx}/logout.do">退出</a>
	<form action="" method="post">
		<label>请先把导入批次：</label> 
		<select name="year">
			<option value="2017">2017</option>
			<option value="2018">2018</option>
			<option value="2019">2019</option>
			<option value="2020">2020</option>
			<option value="2021">2021</option>
		</select>
		<select name="type" id="">
			<option value="1">铁总数据预统计</option>
			<option value="2">铁总职教统计</option>
		</select>
		
	</form>
</body>
</html>