<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="../../css/bootstrap.css">
	<link rel="stylesheet" href="../../css/style.css">
	<script type="text/javascript" src="../../js/jquery-1.12.3.min.js"></script>
	<script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="../../js/bootstrap.min.js"></script>	
	<script type="text/javascript" src="../../js/user/statistics.js"></script>
	<%
	  int typeId = Integer.parseInt(request.getParameter("typeId")); 
	%>
	<script type="text/javascript">
		/*
		 * 对选中的标签激活active状态，对先前处于active状态但之后未被选中的标签取消active
		 * （实现左侧菜单中的标签点击后变色的效果）
		 */
		$(document).ready(function () {
			LLPage_EduStatistics.init(<%=typeId%>);
		});
		
		function ajaxUpload(id,tableId){
			LLPage_EduStatistics.ajaxUpload(id,tableId);
		};
	</script>
</head>
<body>
	<h1 class="page-header">
		<small>数据上传情况：
		</small>
	</h1>
	<div class="table-responsive">
	<table id="blocktable" style="text-align:center;" class="table table-bordered table-striped">
		<thead>
			<tr>
				<th style="text-align:center;">表名</th>
				<th style="text-align:center;">上传情况</th>
				<th style="text-align:center;">操作</th>
				<th style="text-align:center;">模板</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>
	</div>
</body>
</html>