<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
	String context = request.getContextPath();
	url += context;
	application.setAttribute("ctx", url);
%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		
		<!-- 引入各种CSS样式表 -->
		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="css/font-awesome.css">
		<link rel="stylesheet" href="css/index.css">	<!-- 修改自Bootstrap官方Demon，你可以按自己的喜好制定CSS样式 -->
		<link rel="stylesheet" href="css/font-change.css">	<!-- 将默认字体从宋体换成微软雅黑（个人比较喜欢微软雅黑，移动端和桌面端显示效果比较接近） -->		
		
		<script type="text/javascript" src="js/jquery-1.12.3.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
	
		<title>铁总职教数据统计平台 </title>
	</head>
	
	<body>
	<!-- 顶部菜单（来自bootstrap官方Demon）==================================== -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
      		<div class="container">
        		<div class="navbar-header">
	          		<a class="navbar-brand">管理员用户，您好</a>
        		</div>
        		
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">			            
						<li><a href="${ctx}/admin_logout.do"><i class="fa fa-user"></i> 退出登录</a></li>	
						<!-- <li><a href="###" onclick="showAtRight('jsp/user/preStatistics.jsp')"><i class="fa fa-list-alt"></i> 产品列表</a></li>
						<li><a href="###" onclick="showAtRight('jsp/user/eduStatistics.jsp')" ><i class="fa fa-list"></i> 订单列表</a></li> -->	
					</ul>
          			
        		</div>
      		</div>
    	</nav>

	<!-- 左侧菜单选项========================================= -->
		<div class="container-fluid">
			<div class="row-fluie">
				<div class="col-sm-3  sidebar">		
					<ul class="nav nav-sidebar">
						
						
						 <!-- 一级菜单 -->
						<li><a href="#recordMeun" id="collapsedMenu" class="nav-header menu-first collapsed" data-toggle="collapse">
							<i class="fa fa-globe"></i>&nbsp; 统计类别 <span class="sr-only">(current)</span></a>
						</li> 
						<!-- 二级菜单 -->
						<!-- 注意一级菜单中<a>标签内的href="#……"里面的内容要与二级菜单中<ul>标签内的id="……"里面的内容一致 -->
						<ul id="recordMeun" class="nav nav-list collapse menu-second">
						<c:forEach items="${mapRet}" var="node">  
					       <li><a href="#${node.key}" class="nav-header menu-second collapsed" data-toggle="collapse" ><i class="fa fa-list"></i> ${node.key} <span class="sr-only">(current)</span></a></li>
					       <ul id=${node.key} class="nav nav-list collapse menu-third">
					       		<c:forEach items="${node.value}" var="nodeTable"> 
									<li><a href="###" onclick="showJsp('jsp/admin/statistics.jsp',${nodeTable.key},'${nodeTable.value}')" > <i class="fa fa-file-text"></i>&nbsp;${nodeTable.value}</a></li>
								</c:forEach> 
							</ul>
					   	</c:forEach>  
						</ul>	
						<!-- 
						<ul id="recordMeun" class="nav nav-list collapse menu-second">
							<li><a href="#eduMenu" id="collapsedMenu2" class="nav-header menu-second collapsed" data-toggle="collapse" ><i class="fa fa-list"></i> 教育部统计报表<span class="sr-only">(current)</span></a></li>
							<ul id="eduMenu" class="nav nav-list collapse menu-third">
								<li><a href="###" onclick="showJsp('jsp/admin/eduStatistics.jsp')" > <i class="fa fa-file-text"></i>&nbsp; 教育部统计报表1</a></li>
								<li><a href="###" onclick="showJsp('jsp/admin/eduStatistics.jsp')" > <i class="fa fa-file-text"></i>&nbsp; 教育部统计报表2</a></li>
							</ul>
							<li><a href="###" onclick="showJsp('jsp/admin/preStatistics.jsp')" ><i class="fa fa-list"></i> 铁总数据预统计</a></li>
							<li><a href="###" onclick="showJsp('jsp/admin/clerkEduStatistics.jsp')" ><i class="fa fa-list"></i> 铁总职教统计&nbsp;&nbsp;&nbsp; </a></li>
						</ul>	
					 -->						
					</ul>
					
				</div>
			</div>
		</div>

		<!-- 右侧内容展示==================================================  -->
		<div class="col-sm-9 col-sm-offset-3 main">
			<iframe id="iframeContent" name="child" src="jsp/default.jsp"
				style="width: 100%; height:100%;border: 0px"> </iframe>
		</div> 

		<script type="text/javascript">
			
			/*
			 * 对选中的标签激活active状态，对先前处于active状态但之后未被选中的标签取消active
			 * （实现左侧菜单中的标签点击后变色的效果）
			 */
			$(document).ready(function () {
				$('ul.nav > li').click(function (e) {
					//e.preventDefault();	加上这句则导航的<a>标签会失效
					$('ul.nav > li').removeClass('active');
					$(this).addClass('active');
				});
				$("#collapsedMenu").click();
				$(".menu-second").click();
				
			});
			
			function showJsp(jspPath,tableId,tableName){
				document.getElementById("iframeContent").src = jspPath+'?tableId=' + tableId + '&tableName=' + tableName;
			}
		</script>
	
	</body>
</html>