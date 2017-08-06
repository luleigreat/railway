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
	<p>一、验证当前用户是否为"访客"，即未认证（包含未记住）的用户</p>
	<p>二、认证通过或已记住的用户</p>
	 
</body>
</html>